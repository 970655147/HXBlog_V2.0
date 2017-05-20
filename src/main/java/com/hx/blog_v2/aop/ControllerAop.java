package com.hx.blog_v2.aop;

import com.hx.log.util.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Controller层 的AOP
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2016/4/7 11:26
 */
@Aspect
@Component
public class ControllerAop {

    @Pointcut("within(com.hx.blog_v2.controller.*)")
    public void controllerPoint() {

    }

    /**
     * 前置信息处理
     *
     * @param point point
     * @return void
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @Before("controllerPoint()")
    public void doBefore(JoinPoint point) {
//        Log.info("controller - before");
    }

    /**
     * 处理过程拦截
     *
     * @param point point
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @Around("controllerPoint()")
    public Object doProcess(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();
        Object result = point.proceed();
        long cost = System.currentTimeMillis() - begin;

        return result;
    }

    /**
     * 后置信息处理
     *
     * @param point point
     * @return void
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @After("controllerPoint()")
    public void doAfter(JoinPoint point) {
//        Log.info("controller - after");
    }

    /**
     * 异常信息处理
     *
     * @param point point
     * @param e     e
     * @return void
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @AfterThrowing(pointcut = "controllerPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
//        Log.err("controller process failure !");
    }

}
