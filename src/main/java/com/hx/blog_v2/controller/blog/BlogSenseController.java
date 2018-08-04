package com.hx.blog_v2.controller.blog;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.form.blog.BlogSenseForm;
import com.hx.blog_v2.domain.validator.blog.BlogSenseValidator;
import com.hx.blog_v2.service.interf.blog.BlogSenseService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * BlogSenseController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/5/2017 8:41 PM
 */
@RestController
@RequestMapping("/blog/sense")
public class BlogSenseController {

    @Autowired
    private BlogSenseService senseService;
    @Autowired
    private BlogSenseValidator blogSenseValidator;

    @RequestMapping(value = "/sense", method = RequestMethod.POST)
    @BizHandle(handler = "blogSenseHandler")
    @BizLogger
    public Result sense(BlogSenseForm params) {
        Result errResult = blogSenseValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        user = BizUtils.updateUserIfBe(user, params);
        WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);
        params.setUserInfo(user);
        return senseService.sense(params);
    }

}
