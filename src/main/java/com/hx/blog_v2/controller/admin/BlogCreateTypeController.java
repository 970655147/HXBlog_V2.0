package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogCreateTypeSaveForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.BlogCreateTypeSaveValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.service.interf.BlogCreateTypeService;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ImageController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminBlogCreateTypeController")
@RequestMapping("/admin/blog/createType")
public class BlogCreateTypeController {

    @Autowired
    private BlogCreateTypeService blogCreateTypeService;
    @Autowired
    private BlogCreateTypeSaveValidator blogCreateTypeSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(BlogCreateTypeSaveForm params) {
        Result errResult = blogCreateTypeSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return blogCreateTypeService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return blogCreateTypeService.adminList();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(BlogCreateTypeSaveForm params) {
        Result errResult = blogCreateTypeSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return blogCreateTypeService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogCreateTypeService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {
        return blogCreateTypeService.reSort();
    }

}
