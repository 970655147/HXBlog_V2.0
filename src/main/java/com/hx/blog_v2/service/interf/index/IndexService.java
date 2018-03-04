package com.hx.blog_v2.service.interf.index;

import com.hx.blog_v2.service.interf.BaseService;
import com.hx.common.interf.common.Result;

/**
 * IndexService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface IndexService extends BaseService<Object> {

    /**
     * 获取首页的数据
     *
     * @return
     * @author Jerry.X.He
     * @date 5/27/2017 9:15 PM
     * @since 1.0
     */
    Result index();

    /**
     * 获取推荐的博客, 以及最近的博客
     *
     * @return
     * @author Jerry.X.He
     * @date 5/27/2017 9:15 PM
     * @since 1.0
     */
    Result latest();

    /**
     * 获取后台用户可使用的菜单
     *
     * @return
     * @author Jerry.X.He
     * @date 5/27/2017 9:15 PM
     * @since 1.0
     */
    Result adminMenus();

    /**
     * 获取后台用户的统计数据
     *
     * @return
     * @author Jerry.X.He
     * @date 5/30/2017 11:06 AM
     * @since 1.0
     */
    Result adminStatistics();

}
