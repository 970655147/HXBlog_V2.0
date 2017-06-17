package com.hx.blog_v2.domain.po.interf;

/**
 * LogisticalId
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/17/2017 6:22 PM
 */
public interface LogisticalId<T> {

    /**
     * 获取当前实体的逻辑主键
     *
     * @return
     * @author Jerry.X.He
     * @date 6/17/2017 6:22 PM
     * @since 1.0
     */
    T logisticalId();

}
