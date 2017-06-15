package com.hx.blog_v2.aop;

import com.hx.blog_v2.service.interf.ExceptionLogService;
import com.hx.blog_v2.service.interf.RequestLogService;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RequestLogService requestLogService;
    @Autowired
    private ExceptionLogService exceptionLogService;

    @Pointcut("within(com.hx.blog_v2.controller.*)")
    public void controllerPoint() {

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

        requestLogService.saveRequestLog(point, cost);
        if (result instanceof Result) {
            Result rResult = (Result) result;
            if (!rResult.isSuccess()) {
                exceptionLogService.saveExceptionLog(point, rResult, null);
            }
        }
        return result;
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
        e.printStackTrace();
        exceptionLogService.saveExceptionLog(point, null, e);
    }


}
