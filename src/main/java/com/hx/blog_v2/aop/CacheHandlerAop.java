package com.hx.blog_v2.aop;

import com.hx.blog_v2.cache_handler.CacheResultType;
import com.hx.blog_v2.cache_handler.CacheType;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.cache_handler.context.SimpleCacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheHandler;
import com.hx.blog_v2.cache_handler.interf.CacheRequest;
import com.hx.blog_v2.cache_handler.interf.CacheValidator;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.local.service.LocalCacheService;
import com.hx.blog_v2.util.CacheConstants;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimpleResult;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.str.StringUtils;
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
public class CacheHandlerAop {

    @Autowired
    private ApplicationContext ac;
    @Autowired
    private LocalCacheService localCacheService;

    @Pointcut("@annotation(com.hx.blog_v2.cache_handler.anno.CacheHandle)")
    public void cacheHandlerPoint() {

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
    @Around("cacheHandlerPoint()")
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
    @AfterThrowing(pointcut = "cacheHandlerPoint()", throwing = "e")
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
        if (preparedResult != null) {
            Log.info(" beforeHandle intercepted, params : " + JSONArray.fromObject(context.args()).toString());
            return preparedResult;
        }

        CacheHandle cacheHandle = context.cacheHandle();
        String cacheNs = cacheHandle.ns();
        String cacheKey = generateCacheKey(context);

        Object result = null;
        if (!StringUtils.isEmpty(cacheKey)) {
            CacheResultType cacheResultType = cacheHandle.cacheResultType();
            // 其他的集合先不管吧, 妈的 BasePage 发序列化 参数类型不正确, 先处理简单的对象吧
            try {
                if (cacheResultType == CacheResultType.BEAN) {
                    Class resultType = context.method().getReturnType();
                    result = localCacheService.getOrNull(cacheNs, cacheKey, resultType);
                }else if ((cacheResultType == CacheResultType.LIST)) {
                    result = localCacheService.getListOrNull(cacheNs, cacheKey, cacheHandle.cacheResultClass());
                } else if ((cacheResultType == CacheResultType.PAGE)) {
                    result = localCacheService.getPageOrNull(cacheNs, cacheKey, cacheHandle.cacheResultClass());
                } else if ((cacheResultType == CacheResultType.RESULT)) {
                    result = localCacheService.getResultOrNull(cacheNs, cacheKey, cacheHandle.cacheResultClass());
                } else if ((cacheResultType == CacheResultType.RESULT_LIST)) {
                    result = localCacheService.getListResultOrNull(cacheNs, cacheKey, cacheHandle.cacheResultClass());
                } else if ((cacheResultType == CacheResultType.RESULT_PAGE)) {
                    result = localCacheService.getPageResultOrNull(cacheNs, cacheKey, cacheHandle.cacheResultClass());
                }
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
                result = null;
            }
        }
        if (result != null) {
            Log.info(" cache hitted at, ns : " + cacheNs + ", key : " + cacheKey + ", params : " + JSONArray.fromObject(context.args()).toString());
            return result;
        }

        result = point.proceed();
        localCacheService.set(cacheNs, cacheKey, JSONObject.fromObject(result).toString());
        int timeout = cacheHandle.timeout();
        if (timeout > 0) {
            localCacheService.expire(cacheNs, cacheKey, timeout);
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
        CacheHandle cacheHandle = method.getAnnotation(CacheHandle.class);
        CacheContext context = new SimpleCacheContext(point, point.getTarget(), method, point.getArgs(), cacheHandle);
        if (!StringUtils.isEmpty(cacheHandle.validator())) {
            CacheValidator validator = ac.getBean(cacheHandle.validator(), CacheValidator.class);
            if (validator == null) {
                Log.err(" there are no validator matched[" + cacheHandle.validator() + "] ! ");
            }
            context.validator(validator);
        }
        if (!StringUtils.isEmpty(cacheHandle.handler())) {
            CacheHandler handler = ac.getBean(cacheHandle.handler(), CacheHandler.class);
            if (handler == null) {
                Log.err(" there are no handler matched[" + cacheHandle.handler() + "] ! ");
            }
            context.handler(handler);
        }

        return context;
    }

}
