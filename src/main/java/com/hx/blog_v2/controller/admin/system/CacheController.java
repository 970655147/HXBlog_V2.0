package com.hx.blog_v2.controller.admin.system;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.domain.form.system.CacheDetailForm;
import com.hx.blog_v2.domain.form.system.CacheSearchForm;
import com.hx.blog_v2.domain.validator.system.CacheRemoveValidator;
import com.hx.blog_v2.domain.validator.system.CacheSearchValidator;
import com.hx.blog_v2.service.interf.system.CacheService;
import com.hx.common.interf.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * CacheController
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
    @Autowired
    private CacheSearchValidator cacheSearchValidator;
    @Autowired
    private CacheRemoveValidator cacheRemoveValidator;

    @RequestMapping(value = "/localCacheSummary", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result localCacheSummary() {
        return cacheService.localCacheSummary();
    }

    @RequestMapping(value = "/cacheSummary", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result cacheSummary() {
        return cacheService.cacheSummary();
    }

    @RequestMapping(value = "/cacheDetail", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result cacheDetail(CacheSearchForm params) {
        Result errResult = cacheSearchValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return cacheService.cacheDetail(params);
    }

    @RequestMapping(value = "/cacheVisitInfo", method = RequestMethod.GET)
    @BizLogger(req = false, resp = false)
    public Result cacheVisitInfo(CacheDetailForm params) {
        Result errResult = cacheRemoveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return cacheService.cacheVisitInfo(params);
    }

    @RequestMapping(value = "/refreshAll", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshAll() {
        return cacheService.refreshAll();
    }

    @RequestMapping(value = "/refreshAllCached", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshAllCached() {
        return cacheService.refreshAllCached();
    }

    @RequestMapping(value = "/refreshTableCached", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshTableCached() {
        return cacheService.refreshTableCached();
    }

    @RequestMapping(value = "/refreshLocalCached", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshLocalCached() {
        return cacheService.refreshLocalCached();
    }

    @RequestMapping(value = "/refreshStatisticsInfo", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshStatisticsInfo() {
        return cacheService.refreshStatisticsInfo();
    }

    @RequestMapping(value = "/refreshOtherCached", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshOtherCached() {
        return cacheService.refreshOtherCached();
    }

    @RequestMapping(value = "/refreshAllConfigured", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshAllConfigured() {
        return cacheService.refreshAllConfigured();
    }

    @RequestMapping(value = "/refreshSystemConfig", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshSystemConfig() {
        return cacheService.refreshSystemConfig();
    }

    @RequestMapping(value = "/refreshRuleConfig", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshRuleConfig() {
        return cacheService.refreshRuleConfig();
    }

    @RequestMapping(value = "/refreshFrontIdxConfig", method = RequestMethod.POST)
    @BizLogger(req = false, resp = false)
    public Result refreshFrontIdxConfig() {
        return cacheService.refreshFrontIdxConfig();
    }

    @RequestMapping(value = "/cacheRemove", method = RequestMethod.POST)
    @BizLogger
    public Result cacheRemove(CacheDetailForm params) {
        Result errResult = cacheRemoveValidator.validate(params, null);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return cacheService.cacheRemove(params);
    }

}
