package com.hx.blog_v2.aop;

import com.hx.blog_v2.cache_handler.anno.CacheEvictAll;
import com.hx.blog_v2.cache_handler.context.SimpleCacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheHandler;
import com.hx.blog_v2.cache_handler.interf.CacheValidator;
import com.hx.blog_v2.redis.interf.IRedisClientTemplate;
import com.hx.blog_v2.util.BizUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
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
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * BizHandle 的AOP
 *
 * @author Jerry.X.He
 * @version 1.0
 * @date 2016/6/8 23:00
 */
@Aspect
@Component
public class CacheEvictAllAop {

    @Autowired
    private ApplicationContext ac;
    @Autowired
    private IRedisClientTemplate redisClientTemplate;
    @Autowired
    private com.hx.blog_v2.context.CacheContext cacheContext;

    @Pointcut("@annotation(com.hx.blog_v2.cache_handler.anno.CacheEvictAll)")
    public void cacheEvictAllPoint() {

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
    @Around("cacheEvictAllPoint()")
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
    @AfterThrowing(pointcut = "cacheEvictAllPoint()", throwing = "e")
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

        CacheEvictAll cacheEvictAll = context.cacheEvictAll();
        String[] namespaces = cacheEvictAll.ns();
        if (namespaces.length > 0) {
            JSONObject stat = new JSONObject();
            Jedis jedis = redisClientTemplate.getResource().getShard(namespaces[0]);
            for (String ns : namespaces) {
                String keyPattern = redisClientTemplate.getKey(ns) + ":*";
                Set<String> keysToRemove = jedis.keys(keyPattern);
                Long removed = 0L;
                for (String key : keysToRemove) {
                    removed += redisClientTemplate.del(redisClientTemplate.trimPrefix(key));
                }
                stat.put(ns, removed);
                BizUtils.mergeStatsCount(cacheContext.getCacheAopNs2EvictAllCount(), ns, removed.intValue());
            }
            Log.info("[" + point.getSignature().toString() + "] 此次缓存清理, 移除信息如下, stat : " + JSONObject.fromObject(stat).toString());
        }
        return result;
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
        CacheEvictAll cacheEvictAll = method.getAnnotation(CacheEvictAll.class);
        CacheContext context = new SimpleCacheContext(point, point.getTarget(), method, point.getArgs(), cacheEvictAll);
        if (!StringUtils.isEmpty(cacheEvictAll.validator())) {
            CacheValidator validator = ac.getBean(cacheEvictAll.validator(), CacheValidator.class);
            if (validator == null) {
                Log.err(" there are no validator matched[" + cacheEvictAll.validator() + "] ! ");
            }
            context.validator(validator);
        }
        if (!StringUtils.isEmpty(cacheEvictAll.handler())) {
            CacheHandler handler = ac.getBean(cacheEvictAll.handler(), CacheHandler.class);
            if (handler == null) {
                Log.err(" there are no handler matched[" + cacheEvictAll.handler() + "] ! ");
            }
            context.handler(handler);
        }

        return context;
    }

}
