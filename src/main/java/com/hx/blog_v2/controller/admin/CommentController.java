package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.AdminLinkVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(CommentSaveForm params) {

        return commentService.add(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(AdminCommentSearchForm params, SimplePage<AdminCommentVO> page) {

        return commentService.adminList(params, page);
    }

    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public Result floorCommentList(FloorCommentListSearchForm params, SimplePage<CommentVO> page) {

        return commentService.floorCommentList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(CommentSaveForm params) {

        return commentService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return commentService.remove(params);
    }

}
