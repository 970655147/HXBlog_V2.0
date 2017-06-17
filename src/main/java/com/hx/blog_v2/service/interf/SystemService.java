package com.hx.blog_v2.service.interf;

import com.hx.common.interf.common.Result;

/**
 * SystemService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 9:43 AM
 */
public interface SystemService extends BaseService<Object> {

    /**
     * 获取统计数据汇总[近一周的数据 + 5s一组的数据]
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result statsSummary();

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

}
