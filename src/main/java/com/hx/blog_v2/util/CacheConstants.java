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

    /** comment 的本地缓存 */
    public static final String CACHE_AOP_COMMENT = "aopCache:comment";
    /** list:comment 的本地缓存 */
    public static final String CACHE_AOP_LIST_COMMENT = "aopCacheList:comment";
    /** page:comment 的本地缓存 */
    public static final String CACHE_AOP_PAGE_COMMENT = "aopCachePage:comment";
    /** list:adminComment 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_COMMENT = "aopCacheList:adminComment";
    /** page:adminComment 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_PAGE_COMMENT = "aopCachePage:adminComment";
    /** list:floorComment 的本地缓存 */
    public static final String CACHE_AOP_LIST_FLOOR_COMMENT = "aopCacheList:floorComment";

    /** image 的本地缓存 */
    public static final String CACHE_AOP_IMAGE = "aopCache:image";
    /** list:image 的本地缓存 */
    public static final String CACHE_AOP_LIST_IMAGE = "aopCacheList:image";
    /** page:image 的本地缓存 */
    public static final String CACHE_AOP_PAGE_IMAGE = "aopCachePage:image";
    /** list:adminImage 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_IMAGE = "aopCacheList:adminImage";

    /** mood 的本地缓存 */
    public static final String CACHE_AOP_MOOD = "aopCache:mood";
    /** list:mood 的本地缓存 */
    public static final String CACHE_AOP_LIST_MOOD = "aopCacheList:mood";
    /** page:mood 的本地缓存 */
    public static final String CACHE_AOP_PAGE_MOOD = "aopCachePage:mood";
    /** list:adminMood 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_MOOD = "aopCacheList:adminMood";
    /** page:adminMood 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_PAGE_MOOD = "aopCachePage:adminMood";

    /** message 的本地缓存 */
    public static final String CACHE_AOP_MESSAGE = "aopCache:message";
    /** list:message 的本地缓存 */
    public static final String CACHE_AOP_LIST_MESSAGE = "aopCacheList:message";
    /** page:message 的本地缓存 */
    public static final String CACHE_AOP_PAGE_MESSAGE = "aopCachePage:message";
    /** list:adminMessage 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_MESSAGE = "aopCacheList:adminMessage";
    /** page:adminMessage 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_PAGE_MESSAGE = "aopCachePage:adminMessage";

    /** user 的本地缓存 */
    public static final String CACHE_AOP_USER = "aopCache:user";
    /** list:user 的本地缓存 */
    public static final String CACHE_AOP_LIST_USER = "aopCacheList:user";
    /** page:user 的本地缓存 */
    public static final String CACHE_AOP_PAGE_USER = "aopCachePage:user";
    /** list:adminUser 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_USER = "aopCacheList:adminUser";
    /** page:adminUser 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_PAGE_USER = "aopCachePage:adminUser";
    /** page:userId2Name 的本地缓存 */
    public static final String CACHE_AOP_LIST_USER_ID_TO_NAME = "aopCacheList:userId2Name";

    /** correction 的本地缓存 */
    public static final String CACHE_AOP_CORRECTION = "aopCache:correction";
    /** list:correction 的本地缓存 */
    public static final String CACHE_AOP_LIST_CORRECTION = "aopCacheList:correction";
    /** page:correction 的本地缓存 */
    public static final String CACHE_AOP_PAGE_CORRECTION = "aopCachePage:correction";

    /** requestLog 的本地缓存 */
    public static final String CACHE_AOP_REQUEST_LOG = "aopCache:requestLog";
    /** list:requestLog 的本地缓存 */
    public static final String CACHE_AOP_LIST_REQUEST_LOG = "aopCacheList:requestLog";
    /** page:requestLog 的本地缓存 */
    public static final String CACHE_AOP_PAGE_REQUEST_LOG = "aopCachePage:requestLog";

    /** exceptionLog 的本地缓存 */
    public static final String CACHE_AOP_EXCEPTION_LOG = "aopCache:exceptionLog";
    /** list:exceptionLog 的本地缓存 */
    public static final String CACHE_AOP_LIST_EXCEPTION_LOG = "aopCacheList:exceptionLog";
    /** page:exceptionLog 的本地缓存 */
    public static final String CACHE_AOP_PAGE_EXCEPTION_LOG = "aopCachePage:exceptionLog";

    /** systemConfig 的本地缓存 */
    public static final String CACHE_AOP_SYSTEM_CONFIG = "aopCache:systemConfig";
    /** list:systemConfig 的本地缓存 */
    public static final String CACHE_AOP_LIST_SYSTEM_CONFIG = "aopCacheList:systemConfig";
    /** page:systemConfig 的本地缓存 */
    public static final String CACHE_AOP_PAGE_SYSTEM_CONFIG = "aopCachePage:systemConfig";
    /** list:adminSystemConfig 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_LIST_SYSTEM_CONFIG = "aopCacheList:adminSystemConfig";
    /** page:adminSystemConfig 的本地缓存 */
    public static final String CACHE_AOP_ADMIN_PAGE_SYSTEM_CONFIG = "aopCachePage:adminSystemConfig";
    
    /** indexIndex 的本地缓存 */
    public static final String CACHE_AOP_INDEX_INDEX = "aopCache:indexIndex";
    /** indexLatest 的本地缓存 */
    public static final String CACHE_AOP_INDEX_LATEST = "aopCache:indexLatest";

}
