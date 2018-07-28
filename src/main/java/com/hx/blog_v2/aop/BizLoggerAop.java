package com.hx.blog_v2.aop;

import com.hx.blog_v2.biz_log.anno.BizLogger;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.util.BizUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.util.Log;
import com.hx.log.util.Tools;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * BizHandlerAop
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2016/6/8 23:00
 */
@Aspect
@Component
public class BizLoggerAop {

    @Autowired
    private ApplicationContext ac;
    @Value("${biz_logger_on}")
    private boolean bizLoggerOn = true;

    @Pointcut("@annotation(com.hx.blog_v2.biz_log.anno.BizLogger)")
    public void bizLogPoint() {

    }

    /**
     * 处理过程拦截
     * 如果 没有 validatorId 对应的 validator, 默认视为 通过校验
     * handler 必须存在, 如果 不存在, 则忽略当前注解
     *
     * @param point point
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @Around("bizLogPoint()")
    public Object doProcess(ProceedingJoinPoint point) throws Throwable {
        if (!bizLoggerOn) {
            return point.proceed();
        }
        BizLogger bizLogger = getBizLogger(point);
        if (bizLogger == null) {
            return point.proceed();
        }

        String reqId = generateReqId();
        String requestUri = WebContext.getRequest().getRequestURI();
        Object[] args = point.getArgs();
        String methodSignString = point.getSignature().toString();

        long start = System.currentTimeMillis();
        Object result = point.proceed();
        long spent = System.currentTimeMillis() - start;

        StringBuilder logInfo = new StringBuilder();
        if (bizLogger.meta()) {
            logInfo.append("[bizLogger] ------------------------------------- [" + reqId + ", at : " + new Date() + " ] -------------------------------------" + Tools.CRLF);
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", reqUri : " + requestUri + ", [method] : " + methodSignString + ", spent : " + spent + " ms " + Tools.CRLF);
        }
        if (bizLogger.req()) {
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", [req] : " + JSONArray.fromObject(args) + Tools.CRLF);
        }
        if (bizLogger.resp()) {
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", [resp] : " + JSONObject.fromObject(result) + Tools.CRLF);
        }

        if (logInfo.length() > 0) {
            Log.info(logInfo.toString(), false);
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
    @AfterThrowing(pointcut = "bizLogPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        if (!bizLoggerOn) {
            return;
        }
        BizLogger bizLogger = getBizLogger(point);
        if (bizLogger == null) {
            return;
        }

        String reqId = generateReqId();
        String requestUri = WebContext.getRequest().getRequestURI();
        Object[] args = point.getArgs();
        String methodSignString = point.getSignature().toString();

        StringBuilder logInfo = new StringBuilder();
        if (bizLogger.meta()) {
            logInfo.append("[bizLogger] ------------------------------------- [" + reqId + ", at : " + new Date() + " ] -------------------------------------" + Tools.CRLF);
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", reqUri : " + requestUri + ", [method] : " + methodSignString + Tools.CRLF);
        }
        if (bizLogger.req()) {
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", [req] : " + JSONArray.fromObject(args) + Tools.CRLF);
        }
        if (bizLogger.resp()) {
            logInfo.append("[bizLogger] [reqId] : " + reqId + ", [ex] : " + BizUtils.getExceptionInfo(e) + Tools.CRLF);
        }
        if (logInfo.length() > 0) {
            Log.info(logInfo.toString(), false);
        }
        return;
    }

    // --------------------------------------------- 辅助方法 ---------------------------------------------

    /**
     * 生成一个 reqId
     *
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 7/28/2018 7:51 PM
     * @since 1.0
     */
    private String generateReqId() {
        return "req-" + System.currentTimeMillis();
    }

    /**
     * 获取 BizLogger 注解
     *
     * @param point point
     * @return com.hx.blog_v2.biz_log.anno.BizLogger
     * @author Jerry.X.He
     * @date 7/28/2018 7:57 PM
     * @since 1.0
     */
    private BizLogger getBizLogger(JoinPoint point) {
        Signature _sig = point.getSignature();
        MethodSignature sig = null;
        if (!(_sig instanceof MethodSignature)) {
            return null;
        }

        sig = (MethodSignature) _sig;
        Method method = sig.getMethod();
        BizLogger result = method.getAnnotation(BizLogger.class);
        return result;
    }

}
