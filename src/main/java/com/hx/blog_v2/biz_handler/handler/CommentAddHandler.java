package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.handler.common.WebContextAwareableRunnable;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.dto.MessageType;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * BlogSaveHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class CommentAddHandler extends BizHandlerAdapter {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private BlogConstants constants;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public void afterHandle(BizContext context) {
        Result result = (Result) context.result();
        if (result.isSuccess()) {
            Tools.execute(new DoBizRunnable(context, WebContext.getRequest(),
                    WebContext.getResponse(), WebContext.getSession()));
        }
    }


    /**
     * 向给定的 角色的用户发送邮件
     *
     * @param role role
     * @return void
     * @author Jerry.X.He
     * @date 6/27/2017 8:16 PM
     * @since 1.0
     */
    private Result sendMessage(RolePO role, Result result, CommentSaveForm params) {
        Result getBlogResult = blogDao.get(new BeanIdForm(params.getBlogId()));
        if (!getBlogResult.isSuccess()) {
            return getBlogResult;
        }

        MessageSaveForm msgForm = new MessageSaveForm();
        BlogPO blog = (BlogPO) getBlogResult.getData();

        msgForm.setSenderId(constantsContext.contextSystemUserId);
        msgForm.setUserNames(params.getToUser());
        msgForm.setType(MessageType.SYSTEM.code());
        msgForm.setSubject("[HXBlog]评论提醒");
        msgForm.setContent(" 用户 [" + params.getName() + "] 评论了您的文章 : " + params.getComment() + ", " +
                " <a href='" + constantsContext.contextUrlPrefix + "static/main/blogDetail.html?id=" + params.getBlogId() + "'" +
                " color='red' > " +
                blog.getTitle() + "</a>, 请知晓 ! ");
        messageService.add(msgForm);

        msgForm.setUserNames(null);
        msgForm.setRoleIds(role.getId());
        msgForm.setContent(" 用户 [" + params.getName() + "] 为博客 " +
                " <a href='" + constantsContext.contextUrlPrefix + "static/main/blogDetail.html?id=" + params.getBlogId() + "'" +
                " color='red' > " +
                blog.getTitle() + "</a>, " +
                " 新增了一条评论 : " + params.getComment() + ", " +
                "请知晓 ! ");
        return messageService.add(msgForm);
    }

    /**
     * 处理业务的 Runnable
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 7/4/2017 9:31 PM
     */
    private class DoBizRunnable extends WebContextAwareableRunnable {

        public DoBizRunnable(BizContext context,
                             HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
            super(context, req, resp, session);
        }

        @Override
        public void doBiz() {
            Result result = (Result) context.result();
            CommentSaveForm params = (CommentSaveForm) context.args()[0];
            String replyExtracted = BizUtils.extractReplyFrom(params.getComment(),
                    constantsContext.replyCommentPrefix, constantsContext.replyCommentSuffix);
            boolean isReply = ((!Tools.isEmpty(params.getFloorId())) && (replyExtracted != null));
            if (!isReply) {
                Result getExResult = blogExDao.get(new BeanIdForm(params.getBlogId()));
                if (!getExResult.isSuccess()) {
                    result.setExtra(getExResult);
                    return;
                }
                BlogExPO exPo = ((BlogExPO) getExResult.getData());
                exPo.incCommentCnt(1);
                cacheContext.putBlogEx(exPo);
            }

            cacheContext.todaysStatistics().incCommentCnt(1);
            cacheContext.now5SecStatistics().incCommentCnt(1);

            RolePO role = cacheContext.roleByName(constants.roleAdmin);
            if (role != null) {
                Result sendEmailResult = sendMessage(role, result, params);
                if (!sendEmailResult.isSuccess()) {
                    // ignore
                }
            }
        }
    }

}
