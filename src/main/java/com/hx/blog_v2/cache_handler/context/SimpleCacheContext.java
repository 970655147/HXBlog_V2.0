package com.hx.blog_v2.cache_handler.context;


import com.hx.blog_v2.cache_handler.anno.CacheEvict;
import com.hx.blog_v2.cache_handler.anno.CacheEvictAll;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.cache_handler.interf.CacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheHandler;
import com.hx.blog_v2.cache_handler.interf.CacheValidator;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

/**
 * SimpleCacheContext
 *
 * @author Jerry.X.He
 * @date 2018/8/2 14:57
 */
public class SimpleCacheContext implements CacheContext {

    private JoinPoint joinPoint;
    private Object target;
    private Method method;
    private Object[] args;
    private Object result;
    private Throwable exception;
    private CacheHandle cacheHandle;
    private CacheEvict cacheEvict;
    private CacheEvictAll cacheEvictAll;
    private CacheValidator validator;
    private CacheHandler handler;
    private Result validateResult;
    private Object extra;

    public SimpleCacheContext(JoinPoint joinPoint, Object target, Method method, Object[] args, CacheHandle cacheHandle) {
        this.joinPoint = joinPoint;
        this.target = target;
        this.method = method;
        this.args = args;
        this.cacheHandle = cacheHandle;
    }

    public SimpleCacheContext(JoinPoint joinPoint, Object target, Method method, Object[] args, CacheEvict cacheEvict) {
        this.joinPoint = joinPoint;
        this.target = target;
        this.method = method;
        this.args = args;
        this.cacheEvict = cacheEvict;
    }

    public SimpleCacheContext(JoinPoint joinPoint, Object target, Method method, Object[] args, CacheEvictAll cacheEvictAll) {
        this.joinPoint = joinPoint;
        this.target = target;
        this.method = method;
        this.args = args;
        this.cacheEvictAll = cacheEvictAll;
    }

    public SimpleCacheContext() {
    }

    @Override
    public JoinPoint joinPoint() {
        return joinPoint;
    }

    @Override
    public Object target() {
        return target;
    }

    @Override
    public Method method() {
        return method;
    }

    @Override
    public Object[] args() {
        return args;
    }

    @Override
    public Object result() {
        return result;
    }

    @Override
    public Throwable exception() {
        return exception;
    }

    @Override
    public void result(Object result) {
        this.result = result;
    }

    @Override
    public void exception(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public CacheHandle cacheHandle() {
        return cacheHandle;
    }

    @Override
    public CacheEvict cacheEvict() {
        return cacheEvict;
    }

    @Override
    public CacheEvictAll cacheEvictAll() {
        return cacheEvictAll;
    }

    @Override
    public CacheValidator validator() {
        return validator;
    }

    @Override
    public void validator(CacheValidator bizValidator) {
        this.validator = bizValidator;
    }

    @Override
    public CacheHandler handler() {
        return handler;
    }

    @Override
    public void handler(CacheHandler handler) {
        this.handler = handler;
    }

    @Override
    public Result validateResult() {
        return validateResult;
    }

    @Override
    public void validateResult(Result validateResult) {
        this.validateResult = validateResult;
    }

    @Override
    public Object extra() {
        return extra;
    }

    @Override
    public void extra(Object extra) {
        this.extra = extra;
    }
}
