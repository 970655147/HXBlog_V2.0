package com.hx.blog_v2.service.interf.system;

import com.hx.blog_v2.service.interf.BaseService;
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
     * 刷新权限, 该下线的下线, 清理权限相关的缓存
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result refreshAuthority();

}
