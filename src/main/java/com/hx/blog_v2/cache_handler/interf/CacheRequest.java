package com.hx.blog_v2.cache_handler.interf;


import com.hx.common.interf.common.Page;

import java.io.Serializable;

/**
 * CacheRequest
 *
 * @author Jerry.X.He
 * @date 2018/8/3 11:23
 */
public interface CacheRequest extends Serializable {


    /**
     * 生成缓存的时候的 key
     *
     * @param
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 2018/8/2 15:18
     */
    String generateCacheKey();

    /**
     * 是否是分页请求
     *
     * @return boolean
     * @author Jerry.X.He
     * @date 2018/8/2 17:33
     */
    boolean isPageRequest();

    /**
     * 获取分页详细信息
     *
     * @return com.bst.acc.common.dto.common.PageEntity
     * @author Jerry.X.He
     * @date 2018/8/2 17:34
     */
    Page getPageInfo();

}
