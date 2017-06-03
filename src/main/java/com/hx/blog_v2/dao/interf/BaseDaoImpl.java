package com.hx.blog_v2.dao.interf;

import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import com.hx.mongo.dao.interf.MysqlIBaseDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;

/**
 * BaseDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/3/2017 7:26 PM
 */
public abstract class BaseDaoImpl<T extends JSONTransferable<T>> extends MysqlBaseDaoImpl<T> {

    public BaseDaoImpl(T bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BaseDaoImpl(T bean, DbConfig config) {
        super(bean, config);
    }

    public BaseDaoImpl(T bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BaseDaoImpl(T bean) {
        super(bean);
    }

}
