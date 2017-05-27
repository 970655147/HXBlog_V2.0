package com.hx.blog_v2.service.interf;

import com.hx.common.interf.common.Result;

/**
 * BlogService
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

}
