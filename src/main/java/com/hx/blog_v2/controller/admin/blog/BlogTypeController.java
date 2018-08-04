package com.hx.blog_v2.controller.admin.blog;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.blog.BlogTypeSaveForm;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.domain.validator.blog.BlogTypeSaveValidator;
import com.hx.blog_v2.service.interf.blog.BlogTypeService;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * BlogTypeController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminBlogTypeController")
@RequestMapping("/admin/type")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogTypeSaveValidator blogTagSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @BizLogger
    public Result add(BlogTypeSaveForm params) {
        Result errResult = blogTagSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        Result result = blogTypeService.add(params);
        return result;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result list() {
        return blogTypeService.list();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @BizLogger
    public Result update(BlogTypeSaveForm params) {
        Result errResult = blogTagSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }


        return blogTypeService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @BizLogger
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return blogTypeService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    @BizLogger
    public Result reSort() {
        return blogTypeService.reSort();
    }

}
