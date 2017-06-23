package com.hx.blog_v2.util;

import com.hx.log.util.Tools;
import com.hx.mongo.connection.interf.ConnectionContext;
import com.hx.mongo.connection.interf.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * MyMysqlConnectionProvider
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/13/2017 12:21 PM
 */
public class MyMysqlConnectionProvider implements ConnectionProvider<Connection> {

    /**
     * connectionProvider 的实例
     */
    public static MyMysqlConnectionProvider INSTANCE;

    /**
     * 获取 MysqlConnectionProvider 的实例
     *
     * @return com.hx.blog_v2.util.MyMysqlConnectionProvider
     * @author Jerry.X.He
     * @date 6/20/2017 8:24 PM
     * @since 1.0
     */
    public static MyMysqlConnectionProvider getInstance() {
        return INSTANCE;
    }

    @Autowired
    private DataSource dataSource;

    public MyMysqlConnectionProvider() {
        INSTANCE = this;
    }

    @Override
    public Connection getConnection(ConnectionContext<Connection> context) {
        Connection con = null;
        try {
            con = dataSource.getConnection();
        } catch (SQLException se) {
            Tools.assert0("error while try to adminGet an connection !");
        }

        return con;
    }

    @Override
    public void closeConnection(ConnectionContext<Connection> context) {
        try {
            Object[] psAndRs = (Object[]) context.others();
            if(psAndRs != null) {
                for(Object res : psAndRs) {
                    if(res != null) {
                        ((AutoCloseable) res).close();
                    }
                }
            }

//            DataSourceUtils.releaseConnection(context.connection(), dataSource);
            context.connection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
