package com.hx.blog_v2.biz_handler.handler;

import com.hx.blog_v2.biz_handler.handler.common.BizHandlerAdapter;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.dto.EmailContentType;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.po.EmailPO;
import com.hx.blog_v2.service.interf.EmailService;
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
    private EmailService emailService;
    @Autowired
    private CacheContext cacheContext;
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

                EmailPO email = new EmailPO(constantsContext.emailAuthUserName,
                        "970655147@qq.com", "发表博客提醒",
                        user.getName() + " 发表了一篇博客 : " + params.getTitle(),
                        EmailContentType.TEXT_HTML.getType());
                Result sendResult = emailService.sendEmail(email);
                if (!sendResult.isSuccess()) {
                    result.setSuccess(sendResult.isSuccess());
                    result.setCode(sendResult.getCode());
                    result.setMsg(sendResult.getMsg());
                    result.setData(sendResult.getData());
                }
            }
        }
    }

}
