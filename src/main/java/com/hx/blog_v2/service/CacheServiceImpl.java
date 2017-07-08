package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.LocalCacheType;
import com.hx.blog_v2.domain.form.CacheDetailForm;
import com.hx.blog_v2.domain.form.CacheSearchForm;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.CacheService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.cache.CacheEntryFacade;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
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
    /**
     * 缓存的 各个局部缓存的 capacities
     */
    private JSONArray localCachedCapacities;
    /**
     * 本地记录的 localCache 上一次刷新的记录
     */
    private long localCachedLastRefreshTs;

    @Override
    public Result localCacheSummary() {
        JSONObject data = new JSONObject().element("capacities", reloadCapacitiesIfNeed())
                .element("used", cacheContext.localCachedUsed());
        return ResultUtils.success(data);
    }

    @Override
    public Result cacheSummary() {
        JSONObject data = new JSONObject().element("capacities", cacheSummary0());
        return ResultUtils.success(data);
    }

    @Override
    public Result cacheDetail(CacheSearchForm params) {
        LocalCacheType type = LocalCacheType.of(params.getType());
        Result getCacheResult = getCacheByType(type);
        if (!getCacheResult.isSuccess()) {
            return getCacheResult;
        }

        Cache<String, ?> all = (Cache<String, ?>) getCacheResult.getData();
        return cacheDetail0(all);
    }

    @Override
    public Result cacheVisitInfo(CacheDetailForm params) {
        LocalCacheType type = LocalCacheType.of(params.getType());
        Result getCacheResult = getCacheByType(type);
        if (!getCacheResult.isSuccess()) {
            return getCacheResult;
        }

        Cache<String, ?> all = (Cache<String, ?>) getCacheResult.getData();
        CacheEntryFacade<String, ?> entry = all.getEntry(params.getId());
        if (entry == null) {
            return ResultUtils.failed(" 没有这个缓存变量 ! ");
        }
        return cacheVisitInfo0(entry);
    }

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

    @Override
    public Result cacheRemove(CacheDetailForm params) {
        LocalCacheType type = LocalCacheType.of(params.getType());
        Object evicted = null;
        Result getCacheResult = getCacheByType(type);
        if (!getCacheResult.isSuccess()) {
            return getCacheResult;
        }

        Cache<String, ?> all = (Cache<String, ?>) getCacheResult.getData();
        evicted = all.evict(params.getId());
        if (evicted == null) {
            return ResultUtils.failed(" 没有这个缓存变量 ! ");
        }
        return ResultUtils.success(evicted);
    }

    // ----------------- 辅助方法 -----------------------

    /**
     * 如果需要的话, 重新加载 局部缓存的各个容量
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 4:40 PM
     * @since 1.0
     */
    private JSONArray reloadCapacitiesIfNeed() {
        if ((localCachedCapacities == null) || (localCachedLastRefreshTs < cacheContext.localCachedLastRefreshTs())) {
            localCachedCapacities = cacheContext.localCachedCapacities();
            localCachedLastRefreshTs = cacheContext.localCachedLastRefreshTs();
        }

        return localCachedCapacities;
    }

    /**
     * 收集这些相对比较固定的数据的信息
     *
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 6/17/2017 5:44 PM
     * @since 1.0
     */
    private JSONArray cacheSummary0() {
        JSONArray result = cacheContext.cachedCapacities();
        result.addAll(constantsContext.cachedCapacities());
        return result;
    }

    /**
     * 获取当前缓存的所有的信息
     *
     * @param cached cached
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 7/5/2017 9:07 PM
     * @since 1.0
     */
    private <T> Result cacheDetail0(Cache<String, T> cached) {
        JSONObject result = new JSONObject();
        for (String key : cached.keys()) {
            result.put(key, cached.get(key));
        }

        return ResultUtils.success(result);
    }

    /**
     * 获取当前缓存的所有的信息
     *
     * @param entry entry
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 7/5/2017 9:07 PM
     * @since 1.0
     */
    private <T> Result cacheVisitInfo0(CacheEntryFacade<String, T> entry) {
        JSONObject result = new JSONObject();
        result.element("key", entry.key());
        result.element("value", entry.value());
        result.element("accessCount", entry.accessCount());
        result.element("createdAt", entry.createdAt());
        result.element("lastAccessed", entry.lastAccessed());
        result.element("lastUpdated", entry.lastUpdated());
        result.element("ttl", entry.ttl());

        return ResultUtils.success(result);
    }

    /**
     * 根据给定的类型, 获取对应的缓存
     *
     * @param type type
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 7/8/2017 3:24 PM
     * @since 1.0
     */
    private Result getCacheByType(LocalCacheType type) {
        Cache<?, ?> result = null;
        if (LocalCacheType.BLOG_EX == type) {
            result = cacheContext.allBlogEx();
        } else if (LocalCacheType.VISIT_LOG == type) {
            result = cacheContext.allBlogVisitLog();
        } else if (LocalCacheType.BLOG_SENSE == type) {
            result = cacheContext.allBlogSense();
        } else if (LocalCacheType.ROLE_2_RES == type) {
            result = cacheContext.allResourceIdsByRoleIds();
        } else if (LocalCacheType.RES_2_INTERF == type) {
            result = cacheContext.allInterfsByResourceIds();
        } else if (LocalCacheType.UPLOAD_FILE == type) {
            result = cacheContext.allUploadedFile();
        } else if (LocalCacheType.FORCE_OFF_LINE == type) {
            result = cacheContext.allForceOffLine();
        } else if (LocalCacheType.BLACK_LIST == type) {
            result = cacheContext.blackList();
        } else if (LocalCacheType.BLOG == type) {
            result = cacheContext.allBlog();
        } else if (LocalCacheType.BLOG_2_TAG_IDS == type) {
            result = cacheContext.allTagIds();
        } else if (LocalCacheType.BLOG_ID_PAGE_NO_2_COMMENT == type) {
            result = cacheContext.allComment();
        } else {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 没有这个类型 ! ");
        }

        return ResultUtils.success(result);
    }

}
