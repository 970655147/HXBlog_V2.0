package com.hx.blog_v2.controller.admin;

import com.hx.blog_v2.service.interf.CacheService;
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
@RequestMapping("/admin/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @RequestMapping(value = "/refreshAll", method = RequestMethod.GET)
    public Result refreshAll() {
        return cacheService.refreshAll();
    }

    @RequestMapping(value = "/refreshAllCached", method = RequestMethod.GET)
    public Result refreshAllCached() {
        return cacheService.refreshAllCached();
    }

    @RequestMapping(value = "/refreshTableCached", method = RequestMethod.GET)
    public Result refreshTableCached() {
        return cacheService.refreshTableCached();
    }

    @RequestMapping(value = "/refreshLocalCached", method = RequestMethod.GET)
    public Result refreshLocalCached() {
        return cacheService.refreshLocalCached();
    }

    @RequestMapping(value = "/refreshStatisticsInfo", method = RequestMethod.GET)
    public Result refreshStatisticsInfo() {
        return cacheService.refreshStatisticsInfo();
    }

    @RequestMapping(value = "/refreshOtherCached", method = RequestMethod.GET)
    public Result refreshOtherCached() {
        return cacheService.refreshOtherCached();
    }

    @RequestMapping(value = "/refreshAllConfigured", method = RequestMethod.GET)
    public Result refreshAllConfigured() {
        return cacheService.refreshAllConfigured();
    }

    @RequestMapping(value = "/refreshSystemConfig", method = RequestMethod.GET)
    public Result refreshSystemConfig() {
        return cacheService.refreshSystemConfig();
    }

    @RequestMapping(value = "/refreshRuleConfig", method = RequestMethod.GET)
    public Result refreshRuleConfig() {
        return cacheService.refreshRuleConfig();
    }

    @RequestMapping(value = "/refreshFrontIdxConfig", method = RequestMethod.GET)
    public Result refreshFrontIdxConfig() {
        return cacheService.refreshFrontIdxConfig();
    }

    @RequestMapping(value = "/localCacheSummary", method = RequestMethod.GET)
    public Result localCacheSummary() {
        return cacheService.localCacheSummary();
    }

    @RequestMapping(value = "/cacheSummary", method = RequestMethod.GET)
    public Result cacheSummary() {
        return cacheService.cacheSummary();
    }

}
