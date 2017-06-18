package com.hx.blog_v2.controller;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.CommentSaveValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    private CommentSaveValidator commentSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping("/add")
    @BizHandle(handler = "commentAddHandler")
    public Result add(CommentSaveForm params) {
        Result errResult = commentSaveValidator.validate(params, null);
        if(! errResult.isSuccess()) {
            return errResult;
        }
        if(! Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        user = BizUtils.updateUserIfBe(user, params);
        WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);

        return commentService.add(params);
    }

    @RequestMapping("/list")
    public Result list(BeanIdForm params, SimplePage<List<CommentVO>> page) {
        Result errResult = beanIdValidator.validate(params, null);
        if(! errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if(! errResult.isSuccess()) {
            return errResult;
        }

        return commentService.list(params, page);
    }

}
