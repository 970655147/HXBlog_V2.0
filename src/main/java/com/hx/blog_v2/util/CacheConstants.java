package com.hx.blog_v2.util;

import com.hx.log.util.Tools;

/**
 * CacheConstants
 *
 * @author Jerry.X.He
 * @date 2018/7/16 15:51
 */
public final class CacheConstants {

    // disable constructor
    private CacheConstants() {
        Tools.assert0(false, "can't instantiate !");
    }

    /** 缓存所有的数据的 后缀 */
    public static final String CACHE_LOCAL_SUFFIX_ALL = "all";
    /** 缓存所有的路径的分隔符 */
    public static final String CACHE_LOCAL_SEP = "_";
    /** 缓存默认的超时时间 */
    public static final int CACHE_DEFAULT_TIMEOUT = 600;

    /** blog 的本地缓存 */
    public static final String CACHE_AOP_BLOG = "aopCache:blog";
    /** blog 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_BLOG = "aopCache:adminBlog";
    /** list:blog 的本地缓存 */
    public static final String CACHE_AOP_LIST_BLOG = "aopCacheList:blog";
    /** list:adminLlog 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_BLOG = "aopCacheList:adminLlog";
    /** page:blog 的本地缓存 */
    public static final String CACHE_AOP_PAGE_BLOG = "aopCachePage:blog";
    /** page:adminBlog 的本地缓存 */
    public static final String CACHE_AOP_PAGE_ADMIN_BLOG = "aopCachePage:adminBlog";

    /** indexIndex 的本地缓存 */
    public static final String CACHE_AOP_INDEX_INDEX = "aopCache:indexIndex";
    /** indexLatest 的本地缓存 */
    public static final String CACHE_AOP_INDEX_LATEST = "aopCache:indexLatest";

}
