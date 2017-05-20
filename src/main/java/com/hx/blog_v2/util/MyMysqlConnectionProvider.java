package com.hx.blog_v2.util;

import com.hx.log.util.Tools;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MyMysqlConnectionProvider
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/13/2017 12:21 PM
 */
public class MyMysqlConnectionProvider implements ConnectionProvider<Connection> {

    @Override
    public Connection getConnection(DbConfig config) {
        Connection con = null;
        try{
            con = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=UTF8&useSSL=false",
                            config.ip(), config.port(), config.db()),
                    config.username(), config.password() );
        }catch(SQLException se){
            Tools.assert0("error while try to get an connection !");
        }

        return con;
    }
}
