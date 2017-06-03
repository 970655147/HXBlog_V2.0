package com.hx.blog_v2.interceptor;

import com.hx.blog_v2.util.WebContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebContextFilter
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/6/2017 2:56 PM
 */
public class WebContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WebContext.setRequest(request);
        WebContext.setResponse(response);
        WebContext.setSession((request).getSession(true));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);

        WebContext.removeRequest();
        WebContext.removeResponse();
        WebContext.removeSession();
    }
}
