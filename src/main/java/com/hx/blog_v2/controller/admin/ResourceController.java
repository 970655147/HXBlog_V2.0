package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.ResourceSaveForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
import com.hx.blog_v2.domain.validator.BeanIdValidator;
import com.hx.blog_v2.domain.validator.PageValidator;
import com.hx.blog_v2.domain.validator.ResourceSaveValidator;
import com.hx.blog_v2.domain.validator.RoleResourceUpdateValidator;
import com.hx.blog_v2.service.interf.ResourceService;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminResourceController")
@RequestMapping("/admin/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceSaveValidator resourceSaveValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private RoleResourceUpdateValidator roleResourceUpdateValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(ResourceSaveForm params) {
        Result errResult = resourceSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return resourceService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list() {
        return resourceService.adminList();
    }

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public Result treeList(@RequestParam(defaultValue = "false") boolean spread) {
        return resourceService.treeList(spread);
    }

    @RequestMapping(value = "/adminTreeList", method = RequestMethod.GET)
    public Result adminTreeList(@RequestParam(defaultValue = "false") boolean spread) {
        return resourceService.adminTreeList(spread);
    }


    @RequestMapping(value = "/roleResource/list", method = RequestMethod.GET)
    public Result roleResourceList() {
        return resourceService.roleResourceList();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(ResourceSaveForm params) {
        Result errResult = resourceSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return resourceService.update(params);
    }

    @RequestMapping(value = "/roleResource/update", method = RequestMethod.POST)
    public Result roleResourceUpdate(RoleResourceUpdateForm params) {
        Result errResult = roleResourceUpdateValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return resourceService.roleResourceUpdate(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort() {
        return resourceService.reSort();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return resourceService.remove(params);
    }

}
