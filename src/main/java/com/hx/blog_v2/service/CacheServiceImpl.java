package com.hx.blog_v2.service;

import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.CacheService;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CacheServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class CacheServiceImpl extends BaseServiceImpl<Object> implements CacheService {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result refreshAll() {
        constantsContext.refresh();
        cacheContext.refresh();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshAllCached() {
        cacheContext.refresh();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshTableCached() {
        cacheContext.refreshTableCached();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshLocalCached() {
        cacheContext.refreshLocalCached();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshStatisticsInfo() {
        cacheContext.refreshStatisticsInfo();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshOtherCached() {
        cacheContext.refreshOthersCached();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshAllConfigured() {
        constantsContext.refresh();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshSystemConfig() {
        constantsContext.refreshSystemConfig();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshRuleConfig() {
        constantsContext.refreshRuleConfig();
        return ResultUtils.success(" success ");
    }

    @Override
    public Result refreshFrontIdxConfig() {
        constantsContext.refreshFrontIdxConfig();
        return ResultUtils.success(" success ");
    }
}
