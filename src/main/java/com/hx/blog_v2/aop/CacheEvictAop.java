package com.hx.blog_v2.aop;

import com.hx.blog_v2.cache_handler.CacheType;
import com.hx.blog_v2.cache_handler.anno.CacheEvict;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.cache_handler.context.SimpleCacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheHandler;
import com.hx.blog_v2.cache_handler.interf.CacheRequest;
import com.hx.blog_v2.cache_handler.interf.CacheValidator;
import com.hx.blog_v2.domain.BasePageForm;
import com.hx.blog_v2.local.service.LocalCacheService;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.common.interf.common.Result;
import com.hx.log.str.StringUtils;
import com.hx.log.util.Log;
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
import java.util.ArrayList;
import java.util.List;

/**
 * BizHandle 的AOP
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2016/6/8 23:00
 */
@Aspect
@Component
public class CacheEvictAop {

    @Autowired
    private ApplicationContext ac;
    @Autowired
    private LocalCacheService localCacheService;

    @Pointcut("@annotation(com.hx.blog_v2.cache_handler.anno.CacheEvict)")
    public void cacheEvictPoint() {

    }

    /**
     * 处理过程清理 缓存的部分的逻辑
     *
     * @param point point
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 5/19/2017 9:33 PM
     * @since 1.0
     */
    @Around("cacheEvictPoint()")
    public Object doProcess(ProceedingJoinPoint point) throws Throwable {
        CacheContext context = encapCacheContext(point);
        if (context == null) {
            Log.err(" the invocation is not an method ! ");
            return point.proceed();
        }

        CacheValidator validator = context.validator();
        CacheHandler handler = context.handler();
        if (validator != null) {
            Result validateResult = validator.validate(context);
            context.validateResult(validateResult);
            if (!validateResult.isSuccess()) {
                Object result = point.proceed();
                context.result(result);

                if (handler != null) {
                    handler.afterInvalid(context);
                }
                return result;
            }
        }

        if (handler != null) {
            handler.beforeHandle(context);
        }
        Object result = getResult(point, context);
        context.result(result);
        if (handler != null) {
            handler.afterHandle(context);
        }

        return context.result();
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
    @AfterThrowing(pointcut = "cacheEvictPoint()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        CacheContext context = encapCacheContext(point);
        if (context == null) {
            Log.err(" the invocation is not an method ! ");
            return;
        }

        context.exception(e);
        CacheHandler handler = context.handler();
        try {
            if (handler != null) {
                handler.afterException(context);
            }
        } catch (Exception ae) {
            ae.printStackTrace();
            // ignore ;
        }
    }

    // ---------------------------------- 辅助方法 --------------------------------

    /**
     * 尝试 先从 缓存中获取结果, 然后 再直接执行给定的方法获取结果
     *
     * @param point   point
     * @param context context
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 2018/8/2 15:10
     */
    private Object getResult(ProceedingJoinPoint point, CacheContext context) throws Throwable {
        // 如果在 beforeHandle 中设置了结果, 直接返回
        Object preparedResult = context.result();
        Object result = preparedResult;
        if (result == null) {
            result = point.proceed();
        }

        CacheEvict cacheEvict = context.cacheEvict();
        String[] namespaces = cacheEvict.ns();
        String cacheKey = generateCacheKey(context);

        if (!StringUtils.isEmpty(cacheKey)) {
            for (String ns : namespaces) {
                localCacheService.remove(ns, cacheKey);
            }
        }
        return result;
    }

    /**
     * 生成缓存的 key
     *
     * @param context context
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 2018/8/2 16:32
     */
    private String generateCacheKey(CacheContext context) {
        CacheHandle cacheHandle = context.cacheHandle();
        CacheType cacheType = cacheHandle.type();

        if (context.args().length == 0) {
            return CacheConstants.CACHE_LOCAL_SUFFIX_ALL;
        } else {
            if (CacheType.BASE_REQ == cacheType) {
                CacheRequest params = (CacheRequest) context.args()[0];
                return params.generateCacheKey();
            } else if (CacheType.TO_STRING == cacheType) {
                List<String> argsList = new ArrayList<>();
                for (Object arg : context.args()) {
                    argsList.add(String.valueOf(arg));
                }
                return StringUtils.join(argsList, CacheConstants.CACHE_LOCAL_SEP);
            } else if (CacheType.DEV_DEFINED == cacheType) {
                String cacheKey = CacheConstants.CACHE_LOCAL_SUFFIX_ALL;
                if (cacheHandle.others().length > 0) {
                    cacheKey = cacheHandle.others()[0];
                }
                return cacheKey;
            } else if (CacheType.PAGE_DEV_DEFINED == cacheType) {
                String prefix = CacheConstants.CACHE_LOCAL_SUFFIX_ALL;
                if (cacheHandle.others().length > 0) {
                    prefix = cacheHandle.others()[0];
                }
                String pageKey = new BasePageForm().generateCacheKey();
                return prefix + CacheConstants.CACHE_LOCAL_SEP + pageKey;
            }
        }
        return null;
    }

    /**
     * 根据给定的 joinPoint 封装一个 Context 返回 [获取 point, target, method, args, cacheHandle, validator, handler, .. etc]
     *
     * @param point point
     * @return java.lang.Object
     * @author Jerry.X.He
     * @date 6/9/2017 7:31 PM
     * @since 1.0
     */
    private CacheContext encapCacheContext(JoinPoint point) {
        Signature _sig = point.getSignature();
        MethodSignature sig = null;
        if (!(_sig instanceof MethodSignature)) {
            return null;
        }

        sig = (MethodSignature) _sig;
        Method method = sig.getMethod();
        CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
        CacheContext context = new SimpleCacheContext(point, point.getTarget(), method, point.getArgs(), cacheEvict);
        if (!StringUtils.isEmpty(cacheEvict.validator())) {
            CacheValidator validator = ac.getBean(cacheEvict.validator(), CacheValidator.class);
            if (validator == null) {
                Log.err(" there are no validator matched[" + cacheEvict.validator() + "] ! ");
            }
            context.validator(validator);
        }
        if (!StringUtils.isEmpty(cacheEvict.handler())) {
            CacheHandler handler = ac.getBean(cacheEvict.handler(), CacheHandler.class);
            if (handler == null) {
                Log.err(" there are no handler matched[" + cacheEvict.handler() + "] ! ");
            }
            context.handler(handler);
        }

        return context;
    }

}
