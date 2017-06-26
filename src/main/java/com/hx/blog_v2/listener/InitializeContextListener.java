package com.hx.blog_v2.listener;

import com.alibaba.druid.pool.DruidDataSource;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.SpringContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.log.util.Tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

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
        Tools.forceShutdown();

        CacheContext cacheContext = SpringContext.getBean(CacheContext.class);
        cacheContext.clear(CacheContext.REFRESH_ALL_CONFIG);

        DruidDataSource ds = SpringContext.getBean(DruidDataSource.class);
        ds.close();
    }
}
