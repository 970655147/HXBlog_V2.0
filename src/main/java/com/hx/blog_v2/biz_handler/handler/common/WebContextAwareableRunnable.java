package com.hx.blog_v2.biz_handler.handler.common;

import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.context.WebContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WebContextAwareableRunnable
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/5/2017 10:55 PM
 */
public abstract class WebContextAwareableRunnable implements Runnable {

    protected HttpServletRequest req;
    protected HttpServletResponse resp;
    protected HttpSession session;
    protected BizContext context;

   /**
     * 一个能够感知到 上下文的Jerry.X.He7/5/2017 req, resp, session 的 Runnable
     *
     * @param req     req
     * @param resp    resp
     * @param session session
     * @since 1.0
     */
    public WebContextAwareableRunnable(BizContext context,
                                       HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        this.context = context;
        this.req = req;
        this.resp = resp;
        this.session = session;
    }

    @Override
    public void run() {
        WebContext.setRequest(req);
        WebContext.setResponse(resp);
        WebContext.setSession(session);
        doBiz();
        WebContext.removeRequest();
        WebContext.removeResponse();
        WebContext.removeSession();
    }

    /**
     * 处理业务
     *
     * @return
     * @author Jerry.X.He
     * @date 7/5/2017 10:56 PM
     * @since 1.0
     */
    public abstract void doBiz();

}
