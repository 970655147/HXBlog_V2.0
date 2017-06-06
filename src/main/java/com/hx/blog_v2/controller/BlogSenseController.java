package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.service.interf.BlogSenseService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/sense")
    public Result sense(BlogSenseForm params) {

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        BizUtils.updateUserIfBe(user, params);
        WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);
        params.setName(user.getName());
        params.setEmail(user.getEmail());
        return senseService.sense(params);
    }

}
