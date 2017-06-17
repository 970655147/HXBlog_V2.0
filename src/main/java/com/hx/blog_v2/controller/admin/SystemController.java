package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.service.interf.SystemService;
import com.hx.common.interf.common.Result;
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
@RestController
@RequestMapping("/admin/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/statsSummary", method = RequestMethod.GET)
    public Result statsSummary() {
        return systemService.statsSummary();
    }

    @RequestMapping(value = "/localCacheSummary", method = RequestMethod.GET)
    public Result localCacheSummary() {
        return systemService.localCacheSummary();
    }

    @RequestMapping(value = "/cacheSummary", method = RequestMethod.GET)
    public Result cacheSummary() {
        return systemService.cacheSummary();
    }

}
