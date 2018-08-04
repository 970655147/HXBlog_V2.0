package com.hx.blog_v2.cache_handler;

/**
 * CacheType
 *
 * @author Jerry.X.He
 * @date 2018/8/2 14:44
 */
public enum CacheType {

    /**
     * 参数类型继承自 BaseReqDto
     */
    BASE_REQ,

    /**
     * 使用 toString 来处理相关限定
     */
    TO_STRING,

    /**
     * 用户自定义
     */
    DEV_DEFINED,

    /**
     * 分页
     */
    PAGE_DEV_DEFINED,

}
