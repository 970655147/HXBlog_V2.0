package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.validator.*;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminCommentController")
@RequestMapping("/admin/comment")
public class CommentController {

    @Autowired
    private BlogCommentService commentService;
    @Autowired
    private AdminCommentSearchValidator searchValidator;
    @Autowired
    private FloorCommentListSearchValidator floorCommentListSearchValidator;
    @Autowired
    private CommentSaveValidator commentSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BizHandle(handler = "commentAddHandler")
    public Result add(CommentSaveForm params) {
        Result errResult = commentSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return commentService.add(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(AdminCommentSearchForm params, SimplePage<AdminCommentVO> page) {
        Result errResult = searchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return commentService.adminList(params, page);
    }

    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public Result floorCommentList(FloorCommentListSearchForm params, SimplePage<CommentVO> page) {
        Result errResult = floorCommentListSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return commentService.floorCommentList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(CommentSaveForm params) {
        Result errResult = commentSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return commentService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BizHandle(handler = "commentRemoveHandler")
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return commentService.remove(params);
    }

}
