package com.hx.blog_v2.biz_handler.anno;

import com.hx.blog_v2.biz_handler.BizType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用 aop 检测此注解, 处理 相关业务
 * 1. 可以异步处理的业务
 * 2. 减少代码的侵入性的相关业务
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/8/2017 8:50 PM
 */
@Component
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizHandle {

    /**
     * 当前 handler 的type
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 8:56 PM
     * @since 1.0
     */
    BizType type() default BizType.OTHER;

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
    String handler();

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
