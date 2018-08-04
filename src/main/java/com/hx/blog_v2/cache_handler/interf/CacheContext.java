package com.hx.blog_v2.cache_handler.interf;

import com.hx.blog_v2.cache_handler.anno.CacheEvict;
import com.hx.blog_v2.cache_handler.anno.CacheEvictAll;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

/**
 * CacheContext
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 2018/8/2 14:57
 */
public interface CacheContext {

    /**
     * 获取上下文的 joinPoint
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:03 PM
     * @since 1.0
     */
    JoinPoint joinPoint();

    /**
     * 获取当前触发方法的对象
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:08 PM
     * @since 1.0
     */
    Object target();

    /**
     * 获取当前正在执行的方法
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:08 PM
     * @since 1.0
     */
    Method method();

    /**
     * 当前方法触发的参数列表
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:02 PM
     * @since 1.0
     */
    Object[] args();

    /**
     * 当前方法的返回结果
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:02 PM
     * @since 1.0
     */
    Object result();

    /**
     * 当前方法产生的异常
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:02 PM
     * @since 1.0
     */
    Throwable exception();

    /**
     * 配置当前方法的返回结果
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:02 PM
     * @since 1.0
     */
    void result(Object result);

    /**
     * 配置当前方法产生的异常
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:02 PM
     * @since 1.0
     */
    void exception(Throwable exception);

    /**
     * 获取 @CacheHandle
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:11 PM
     * @since 1.0
     */
    CacheHandle cacheHandle();

    /**
     * 获取 @CacheEvict
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:11 PM
     * @since 1.0
     */
    CacheEvict cacheEvict();

    /**
     * 获取 @CacheEvictAll
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:11 PM
     * @since 1.0
     */
    CacheEvictAll cacheEvictAll();

    /**
     * 获取当前处理业务的 BizValidator
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    CacheValidator validator();

    /**
     * 配置当前处理业务的 BizValidator
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    void validator(CacheValidator bizValidator);

    /**
     * 获取当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    CacheHandler handler();

    /**
     * 配置当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    void handler(CacheHandler handler);

    /**
     * 获取当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    Result validateResult();

    /**
     * 配置当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    void validateResult(Result validateResult);

    /**
     * 获取 extra
     *
     * @return
     * @author Jerry.X.He
     * @date 2018/4/26 12:30
     */
    Object extra();

    /**
     * 配置 extra
     *
     * @param extra extra
     * @return
     * @author Jerry.X.He
     * @date 2018/4/26 12:30
     */
    void extra(Object extra);

}
