package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.SystemService;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<Object> implements SystemService {

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
    public Result statsSummary() {
        JSONObject data = new JSONObject().element("lastWeekInfo", cacheContext.recentlyStatistics())
                .element("todayInfo", cacheContext.todaysStatistics())
                .element("realTimeInfo", cacheContext.all5SecStatistics());
        return ResultUtils.success(data);
    }

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

}
