package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.BlogSaveValidator;
import com.hx.blog_v2.domain.validator.BlogSearchValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.domain.vo.AdminBlogVO;
import com.hx.blog_v2.service.interf.BlogService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
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
@RestController("adminBlogController")
@RequestMapping("/admin/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private BlogSaveValidator blogSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private BlogSearchValidator blogSearchValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BizHandle(handler = "blogSaveHandler")
    public Result save(BlogSaveForm params) {
        Result errResult = blogSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if(! Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return blogService.save(params);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogService.adminGet(params);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(BlogSearchForm params, SimplePage<AdminBlogVO> page) {
        Result errResult = blogSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogService.adminList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(BlogSaveForm params) {
        Result errResult = blogSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if(Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return blogService.save(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BizHandle(handler = "blogRemoveHandler")
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogService.remove(params);
    }

}
