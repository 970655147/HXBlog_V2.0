package com.hx.blog_v2.listener;

import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.WebContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * InitializeContextListener
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 6:02 PM
 */
public class InitializeContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        WebContext.init(servletContextEvent.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
