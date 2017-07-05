package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.CacheRemoveForm;
import com.hx.blog_v2.domain.form.CacheSearchForm;
import com.hx.common.interf.common.Result;

/**
 * CacheService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/17/2017 12:00 PM
 */
public interface CacheService {


    /**
     * 获取局部缓存的使用数据
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result localCacheSummary();

    /**
     * 获取缓存的使用数据的信息
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result cacheSummary();

    /**
     * 获取缓存的详细信息
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result cacheDetail(CacheSearchForm params);

    /**
     * 刷新所有的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshAll();

    /**
     * 刷新所有的 CacheContext 的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshAllCached();

    /**
     * 刷新缓存了表的所有的数据的缓存
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshTableCached();

    /**
     * 刷新缓存缓存了部分数据的的缓存
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshLocalCached();

    /**
     * 刷新统计数据
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshStatisticsInfo();

    /**
     * 刷新统计数据
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshOtherCached();

    /**
     * 刷新所有的 ConstantsContext 的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshAllConfigured();

    /**
     * 刷新所有的 ConstantsContext 的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshSystemConfig();

    /**
     * 刷新所有的 系统相关配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshRuleConfig();

    /**
     * 刷新所有的 前台索引界面的配置
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshFrontIdxConfig();

    /**
     * 获取缓存的详细信息
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result cacheRemove(CacheRemoveForm params);

}
