package com.hx.blog_v2.cache_handler;

/**
 * CacheType
 *
 * @author Jerry.X.He
 * @date 2018/8/2 14:44
 */
public enum CacheResultType {

    /**
     * 普通的 bean
     */
    BEAN,

    /**
     * List 集合
     */
    LIST,

    /**
     * BasePage 集合
     */
    PAGE,

    /**
     * Result
     */
    RESULT,

    /**
     * Result 中 data 为 List 的集合
     */
    RESULT_LIST,

    /**
     * Result 中 data 为 Page 的集合
     */
    RESULT_PAGE,

}
