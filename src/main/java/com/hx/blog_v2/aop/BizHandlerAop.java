package com.hx.blog_v2.aop;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.biz_handler.context.SimpleBizContext;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.biz_handler.interf.BizHandler;
import com.hx.blog_v2.biz_handler.interf.BizValidator;
import com.hx.common.interf.common.Result;
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
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * BizHandle 的AOP
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2016/6/8 23:00
 */
@Aspect
@Component
public class BizHandlerAop {

    @Autowired
    private ApplicationContext ac;

    @Pointcut("@annotation(com.hx.blog_v2.biz_handler.anno.BizHandle)")
    public void bizHandlerPoint() {

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
    @Around("bizHandlerPoint()")
    public Object doProcess(ProceedingJoinPoint point) throws Throwable {
        BizContext context = encapBizContext(point);
        if (context == null) {
            Log.err(" the invocation is not an method ! ");
            return point.proceed();
        }

        BizValidator validator = context.validator();
        BizHandler handler = context.handler();
        if (handler == null) {
            Log.err(" there are no handler matched[" + context.bizHandle().handler() + "] ! ");
            return point.proceed();
        }
        if (validator != null) {
            Result validateResult = validator.validate(context);
            context.validateResult(validateResult);
            if (!validateResult.isSuccess()) {
                Object result = point.proceed();
                context.result(result);
                handler.afterInvalid(context);
                return result;
            }
        }

        handler.beforeHandle(context);
        Object result = point.proceed();
        context.result(result);
        handler.afterHandle(context);

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
    @AfterThrowing(pointcut = "bizHandlerPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        e.printStackTrace();
        BizContext context = encapBizContext(point);
        if (context == null) {
            Log.err(" the invocation is not an method ! ");
            return;
        }

        context.exception(e);
        BizHandler handler = context.handler();
        if (handler == null) {
            Log.err(" there are no handler matched[" + context.bizHandle().handler() + "] ! ");
            return;
        }
        handler.afterException(context);
    }

    /**
     * 根据给定的 joinPoint 封装一个 Context 返回 [获取 point, target, method, args, bizHandle, validator, handler, .. etc]
     *
     * @param point point
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 7:31 PM
     * @since 1.0
     */
    private BizContext encapBizContext(JoinPoint point) {
        Signature _sig = point.getSignature();
        MethodSignature sig = null;
        if (!(_sig instanceof MethodSignature)) {
            return null;
        }

        sig = (MethodSignature) _sig;
        Method method = sig.getMethod();
        BizHandle bizHandle = method.getAnnotation(BizHandle.class);
        BizContext context = new SimpleBizContext(point, point.getTarget(), method, point.getArgs(), bizHandle);
        if (!Tools.isEmpty(bizHandle.validator())) {
            BizValidator validator = ac.getBean(bizHandle.validator(), BizValidator.class);
            if (validator == null) {
                Log.err(" there are no validator matched[" + bizHandle.validator() + "] ! ");
            }
            context.validator(validator);
        }
        BizHandler handler = ac.getBean(bizHandle.handler(), BizHandler.class);
        context.handler(handler);

        return context;
    }

}
