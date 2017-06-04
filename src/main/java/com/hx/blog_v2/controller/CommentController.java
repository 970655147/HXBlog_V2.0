package com.hx.blog_v2.controller;

import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.WebContext;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommentController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:04 AM
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private BlogCommentService commentService;

    @RequestMapping("add")
    public Result add(CommentSaveForm params) {

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if(user == null) {
            user = new SessionUser(params.getName(), params.getEmail(), params.getHeadImgUrl(), "guest", "guest", false);
        } else {
            if(! user.isSystemUser()) {
                user.setUserName(params.getName());
                user.setEmail(params.getEmail());
            }
            user.setHeadImgUrl(params.getHeadImgUrl());
        }
        WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);

        return commentService.add(params);
    }


}
