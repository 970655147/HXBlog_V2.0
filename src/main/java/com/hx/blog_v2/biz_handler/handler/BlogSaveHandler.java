package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.dto.MessageType;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
import com.hx.blog_v2.domain.po.RolePO;
import com.hx.blog_v2.service.interf.MessageService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * BlogSaveHandler
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 12:30 AM
 */
@Component
public class BlogSaveHandler extends BizHandlerAdapter {

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
            if ((context.args().length > 0) && (context.args()[0] instanceof BlogSaveForm)) {
                BlogSaveForm params = (BlogSaveForm) context.args()[0];
                SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);

                // add
                if (Tools.isEmpty(params.getId())) {
                    cacheContext.todaysStatistics().incBlogCnt(1);
                    cacheContext.now5SecStatistics().incBlogCnt(1);
                    Date now = new Date();
                    String createdAtMonth = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM);
                    cacheContext.updateBlogInMonthFacet(createdAtMonth, true);
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

    /**
     * 向给定的 角色的用户发送邮件
     *
     * @param role role
     * @return void
     * @author Jerry.X.He
     * @date 6/27/2017 8:16 PM
     * @since 1.0
     */
    private Result sendMessage(RolePO role, Result result, BlogSaveForm params) {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        MessageSaveForm msgForm = new MessageSaveForm();

        msgForm.setSenderId(constantsContext.contextSystemUserId);
        msgForm.setRoleIds(role.getId());
        msgForm.setType(MessageType.SYSTEM.getType());
        msgForm.setSubject("[HXBlog]博客提醒");
        msgForm.setContent(" 用户 [" + user.getName() + "] 发表了一篇博客 : " +
                " <a href='" + constantsContext.contextUrlPrefix + "static/main/blogDetail.html?id=" + result.getData() + "'" +
                " color='red' > " +
                params.getTitle() + "</a>, 请注意审核该内容 ! ");
        return messageService.add(msgForm);
    }

}
