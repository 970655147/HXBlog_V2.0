package com.hx.blog_v2.biz_log.anno;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BizLogger
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/28/2018 7:21 PM
 */
@Component
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizLogger {

    // 暂时作为一个标记注解

    /**
     * 是否打印元数据信息
     *
     * @return true
     */
    boolean meta() default true;

    /**
     * 是否打印输入参数信息
     *
     * @return true
     */
    boolean req() default true;

    /**
     * 是否打印输出参数信息
     *
     * @return true
     */
    boolean resp() default true;

}
