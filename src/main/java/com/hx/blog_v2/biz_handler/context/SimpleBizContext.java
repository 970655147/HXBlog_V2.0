package com.hx.blog_v2.biz_handler.context;

import com.hx.blog_v2.biz_handler.anno.BizHandle;
import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.biz_handler.interf.BizHandler;
import com.hx.blog_v2.biz_handler.interf.BizValidator;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * SimpleBizContext
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 7:06 PM
 */
public class SimpleBizContext implements BizContext {

    private JoinPoint joinPoint;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private Object target;
    private Method method;
    private Object[] args;
    private Object result;
    private Throwable exception;
    private BizHandle bizHandle;
    private BizValidator validator;
    private BizHandler handler;
    private Result validateResult;

    public SimpleBizContext(JoinPoint joinPoint, HttpServletRequest req, HttpServletResponse resp,
                            Object target, Method method, Object[] args, BizHandle bizHandle) {
        this.joinPoint = joinPoint;
        this.req = req;
        this.resp = resp;
        this.target = target;
        this.method = method;
        this.args = args;
        this.bizHandle = bizHandle;
    }

    public SimpleBizContext(JoinPoint joinPoint, Object target, Method method, Object[] args,
                            BizHandle bizHandleAnno) {
        this(joinPoint, WebContext.getRequest(), WebContext.getResponse(), target, method, args, bizHandleAnno);
    }

    public SimpleBizContext() {
    }

    @Override
    public JoinPoint joinPoint() {
        return joinPoint;
    }

    @Override
    public HttpServletRequest request() {
        return req;
    }

    @Override
    public HttpServletResponse response() {
        return resp;
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
    public BizHandle bizHandle() {
        return bizHandle;
    }

    @Override
    public BizValidator validator() {
        return validator;
    }

    @Override
    public void validator(BizValidator bizValidator) {
        this.validator = bizValidator;
    }

    @Override
    public BizHandler handler() {
        return handler;
    }

    @Override
    public void handler(BizHandler handler) {
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
}
