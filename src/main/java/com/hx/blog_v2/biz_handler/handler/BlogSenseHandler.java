package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.handler.common.WebContextAwareableRunnable;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.dao.interf.BlogSenseDao;
import com.hx.blog_v2.domain.common.message.MessageType;
import com.hx.blog_v2.domain.form.blog.BlogSenseForm;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.message.MessageSaveForm;
import com.hx.blog_v2.domain.po.blog.BlogExPO;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.domain.po.blog.BlogSensePO;
import com.hx.blog_v2.domain.po.resources.RolePO;
import com.hx.blog_v2.service.interf.message.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * BlogSenseHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class BlogSenseHandler extends BizHandlerAdapter {

    @Autowired
    private BlogDao blogDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private BlogSenseDao senseDao;

    @Autowired
    private MessageService messageService;
    @Autowired
    private BlogConstants constants;
    @Autowired
    private ConstantsContext constantsContext;
    @Autowired
    private CacheContext cacheContext;

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
    private Result sendMessage(RolePO role, Result result, BlogSenseForm params) {
        Result getBlogResult = blogDao.get(new BeanIdForm(params.getBlogId()));
        if (!getBlogResult.isSuccess()) {
            return getBlogResult;
        }

        MessageSaveForm msgForm = new MessageSaveForm();
        BlogPO blog = (BlogPO) getBlogResult.getData();
        msgForm.setSenderId(constantsContext.contextSystemUserId);
        msgForm.setUserNames(blog.getAuthor());
        msgForm.setType(MessageType.SYSTEM.code());
        msgForm.setSubject("[HXBlog]打分提醒");
        msgForm.setContent(" 用户 [" + params.getName() + "] 为您的文章打了 " + params.getScore() + "分 !,  " +
                " <a href='" + constantsContext.contextUrlPrefix + "static/main/blogDetail.html?id=" + params.getBlogId() + "'" +
                " color='red' > " +
                blog.getTitle() + "</a>, 请知晓 ! ");
        return messageService.add(msgForm);
    }

    /**
     * 更新博客维护的额外的点赞信息
     *
     * @param params params
     * @return void
     * @author Jerry.X.He
     * @date 6/6/2017 8:53 PM
     * @since 1.0
     */
    private Result updateBlogEx(BlogSensePO oldPo, BlogSenseForm params) {
        Result getResult = blogExDao.get(new BeanIdForm(params.getBlogId()));
        if (!getResult.isSuccess()) {
            return getResult;
        }

        BlogExPO po = (BlogExPO) getResult.getData();
        if (oldPo != null) {
            po.decGoodCnt(oldPo.getScore(), -1);
        }
        if (params.getScore() > 0) {
            po.incGoodCnt(params.getScore(), 1);
        }
        return ResultUtils.success("success");
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
            BlogSenseForm params = (BlogSenseForm) context.args()[0];
            BlogSensePO oldPo = (BlogSensePO) WebContext.getAttributeFromRequest(BlogConstants.REQUEST_EXTRA);
            Result updateExResult = updateBlogEx(oldPo, params);
            if (oldPo != null) {
                oldPo.setScore(params.getScore());
                Result updateSenseResult = senseDao.update(oldPo);
                if (!updateSenseResult.isSuccess()) {
                    return;
                }
            }
            if (!updateExResult.isSuccess()) {
                return;
            }

            if (params.getScore() > 3) {
                cacheContext.todaysStatistics().incGoodCnt(1);
                cacheContext.now5SecStatistics().incGoodCnt(1);
//                    cacheContext.todaysStatistics().incNotGoodCnt(-1);
            } else {
//                    cacheContext.todaysStatistics().incGoodCnt(-1);
                cacheContext.todaysStatistics().incNotGoodCnt(1);
                cacheContext.now5SecStatistics().incNotGoodCnt(1);
            }

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
