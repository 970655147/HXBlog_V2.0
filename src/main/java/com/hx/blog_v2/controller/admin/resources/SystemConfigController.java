package com.hx.blog_v2.controller.admin.resources;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.system.SystemConfigSearchForm;
import com.hx.blog_v2.domain.validator.common.BeanIdValidator;
import com.hx.blog_v2.domain.validator.common.PageValidator;
import com.hx.blog_v2.domain.validator.system.SystemConfigSaveValidator;
import com.hx.blog_v2.domain.validator.system.SystemConfigSearchValidator;
import com.hx.blog_v2.domain.vo.system.SystemConfigVO;
import com.hx.blog_v2.service.interf.system.SystemConfigService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * SystemConfigController
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 4:57 PM
 */
@RestController("adminSystemConfigController")
@RequestMapping("/admin/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private SystemConfigSaveValidator systemConfigSaveValidator;
    @Autowired
    private SystemConfigSearchValidator systemConfigSearchValidator;
    @Autowired
    private BeanIdValidator beanIdValidator;
    @Autowired
    private PageValidator pageValidator;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(SystemConfigSaveForm params) {
        Result errResult = systemConfigSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return systemConfigService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SystemConfigSearchForm params, SimplePage<SystemConfigVO> page) {
        Result errResult = systemConfigSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = pageValidator.validate(page, null);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 不为空 ! ");
        }

        return systemConfigService.adminList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(SystemConfigSaveForm params) {
        Result errResult = systemConfigSaveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (Tools.isEmpty(params.getId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 为空 ! ");
        }

        return systemConfigService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {
        Result errResult = beanIdValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return systemConfigService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort(SystemConfigSearchForm params) {
        return systemConfigService.reSort(params);
    }

}
