package com.hx.blog_v2.cache_handler.interf;


/**
 * BizValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 2018/8/2 14:57
 */
public interface CacheHandler {

    /**
     * 没有通过 validator 的校验的情况下需要处理的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:00 PM
     * @since 1.0
     */
    void afterInvalid(CacheContext context);

    /**
     * 对应的 target 处理业务之前 需要处理的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:00 PM
     * @since 1.0
     */
    void beforeHandle(CacheContext context);

    /**
     * 对应的 target 处理业务之后 需要处理的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:00 PM
     * @since 1.0
     */
    void afterHandle(CacheContext context);

    /**
     * 对应的 target 处理业务发生异常之后 需要处理的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:00 PM
     * @since 1.0
     */
    void afterException(CacheContext context);

}
