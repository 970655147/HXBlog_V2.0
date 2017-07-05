package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.LocalCacheType;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CacheRemoveForm;
import com.hx.blog_v2.domain.form.CacheSearchForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.blog_v2.domain.po.UploadFilePO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.CacheService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (LocalCacheType.BLOG_EX == type) {
            Cache<String, BlogExPO> all = cacheContext.allBlogEx();
            return cacheDetail0(all);
        } else if (LocalCacheType.VISIT_LOG == type) {
            Cache<String, BlogVisitLogPO> all = cacheContext.allBlogVisitLog();
            return cacheDetail0(all);
        } else if (LocalCacheType.BLOG_SENSE == type) {
            Cache<String, BlogSensePO> all = cacheContext.allBlogSense();
            return cacheDetail0(all);
        } else if (LocalCacheType.ROLE_2_RES == type) {
            Cache<String, List<String>> all = cacheContext.allResourceIdsByRoleIds();
            return cacheDetail0(all);
        } else if (LocalCacheType.RES_2_INTERF == type) {
            Cache<String, List<String>> all = cacheContext.allInterfsByResourceIds();
            return cacheDetail0(all);
        } else if (LocalCacheType.UPLOAD_FILE == type) {
            Cache<String, UploadFilePO> all = cacheContext.allUploadedFile();
            return cacheDetail0(all);
        } else if (LocalCacheType.FORCE_OFF_LINE == type) {
            Cache<String, String> all = cacheContext.allForceOffLine();
            return cacheDetail0(all);
        } else if (LocalCacheType.BLACK_LIST == type) {
            Cache<String, String> all = cacheContext.blackList();
            return cacheDetail0(all);
        } else {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 没有这个类型 ! ");
        }
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
    public Result cacheRemove(CacheRemoveForm params) {
        LocalCacheType type = LocalCacheType.of(params.getType());
        Object evicted = null;
        if (LocalCacheType.BLOG_EX == type) {
            Cache<String, BlogExPO> all = cacheContext.allBlogEx();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.VISIT_LOG == type) {
            Cache<String, BlogVisitLogPO> all = cacheContext.allBlogVisitLog();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.BLOG_SENSE == type) {
            Cache<String, BlogSensePO> all = cacheContext.allBlogSense();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.ROLE_2_RES == type) {
            Cache<String, List<String>> all = cacheContext.allResourceIdsByRoleIds();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.RES_2_INTERF == type) {
            Cache<String, List<String>> all = cacheContext.allInterfsByResourceIds();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.UPLOAD_FILE == type) {
            Cache<String, UploadFilePO> all = cacheContext.allUploadedFile();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.FORCE_OFF_LINE == type) {
            Cache<String, String> all = cacheContext.allForceOffLine();
            evicted = all.evict(params.getId());
        } else if (LocalCacheType.BLACK_LIST == type) {
            Cache<String, String> all = cacheContext.blackList();
            evicted = all.evict(params.getId());
        } else {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 没有这个类型 ! ");
        }

        if(evicted == null) {
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

}
