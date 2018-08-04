package com.hx.blog_v2.cache_handler.anno;

import com.hx.blog_v2.cache_handler.CacheResultType;
import com.hx.blog_v2.cache_handler.CacheType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * CacheHandle
 *
 * @author Jerry.X.He
 * @date 2018/8/2 14:57
 */
@Component
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheHandle {

    /**
     * 当前缓存的类型
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/8/2 14:45
     */
    CacheType type();

    /**
     * 缓存的 结果的类型
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/8/2 16:26
     */
    CacheResultType cacheResultType() default CacheResultType.RESULT;

    /**
     * 缓存的 结果的类型
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/8/2 16:26
     */
    Class cacheResultClass();

    /**
     * 当前缓存的名称空间
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/8/2 14:42
     */
    String ns();

    /**
     * 当前缓存的超时时间 [小于0表示不过期]
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/8/2 14:42
     */
    int timeout() default -1;

    /**
     * 获取校验上下文的filter 在 spring 容器中的id
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 8:51 PM
     * @since 1.0
     */
    String validator() default "";

    /**
     * 获取实际处理业务的 handler 在 spring 容器中的id
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 8:52 PM
     * @since 1.0
     */
    String handler() default "";

    /**
     * 一些其他的根据上下文所需, 需要传入的数据
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 8:52 PM
     * @since 1.0
     */
    String[] others() default {};

}
