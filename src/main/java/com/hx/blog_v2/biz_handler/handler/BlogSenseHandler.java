package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.domain.dto.MessageType;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BlogSaveHandler
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
            Tools.execute(new DoBizRunnable(context));
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
     * 处理业务的 Runnable
     *
     * @author Jerry.X.He <970655147@qq.com>
     * @version 1.0
     * @date 7/4/2017 9:31 PM
     */
    private class DoBizRunnable implements Runnable {
        private BizContext context;

        public DoBizRunnable(BizContext context) {
            this.context = context;
        }

        @Override
        public void run() {
            Result result = (Result) context.result();
            BlogSenseForm params = (BlogSenseForm) context.args()[0];
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
