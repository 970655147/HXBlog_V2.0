package com.hx.blog_v2.cache_handler.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 清理缓存的 标记
 *
 * @author Jerry.X.He
 * @date 2018/7/31 14:01
 */
@Component
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheEvictAll {

    /**
     * 需要 清理的部分数据的名称空间
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/7/31 14:02
     */
    String[] ns();

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
     * @date 2018/7/31 14:02
     * @since 1.0
     */
    String[] others() default {};

}
