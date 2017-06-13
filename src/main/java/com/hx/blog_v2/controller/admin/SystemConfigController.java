package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.SystemConfigSaveForm;
import com.hx.blog_v2.domain.form.SystemConfigSearchForm;
import com.hx.blog_v2.domain.vo.SystemConfigVO;
import com.hx.blog_v2.service.interf.SystemConfigService;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
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
@RestController("adminSystemConfigController")
@RequestMapping("/admin/config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(SystemConfigSaveForm params) {

        return systemConfigService.add(params);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Result list(SystemConfigSearchForm params, SimplePage<SystemConfigVO> page) {

        return systemConfigService.adminList(params, page);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(SystemConfigSaveForm params) {

        return systemConfigService.update(params);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Result remove(BeanIdForm params) {

        return systemConfigService.remove(params);
    }

    @RequestMapping(value = "/reSort", method = RequestMethod.POST)
    public Result reSort(SystemConfigSearchForm params) {

        return systemConfigService.reSort(params);
    }

}
