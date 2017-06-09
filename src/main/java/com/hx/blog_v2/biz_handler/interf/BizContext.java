package com.hx.blog_v2.biz_handler.interf;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * BizContext
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/8/2017 9:00 PM
 */
public interface BizContext {

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
     * 获取当前 request
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:10 PM
     * @since 1.0
     */
    HttpServletRequest request();

    /**
     * 获取当前 response
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:10 PM
     * @since 1.0
     */
    HttpServletResponse response();

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
     * 获取BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:11 PM
     * @since 1.0
     */
    BizHandle bizHandle();

    /**
     * 获取当前处理业务的 BizValidator
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    BizValidator validator();

    /**
     * 配置当前处理业务的 BizValidator
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    void validator(BizValidator bizValidator);

    /**
     * 获取当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    BizHandler handler();

    /**
     * 配置当前处理业务的 BizHandler
     *
     * @return
     * @author Jerry.X.He
     * @date 6/9/2017 7:28 PM
     * @since 1.0
     */
    void handler(BizHandler handler);

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

}
