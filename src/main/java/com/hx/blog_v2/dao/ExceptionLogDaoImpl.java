package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.ExceptionLogDao;
import com.hx.blog_v2.domain.po.ExceptionLogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

/**
 * BlogTagDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class ExceptionLogDaoImpl extends MysqlBaseDaoImpl<ExceptionLogPO, Integer> implements ExceptionLogDao {

    public ExceptionLogDaoImpl(ExceptionLogPO bean) {
        super(bean);
    }

    public ExceptionLogDaoImpl(ExceptionLogPO bean, DbConfig config) {
        super(bean, config);
    }

    public ExceptionLogDaoImpl(ExceptionLogPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public ExceptionLogDaoImpl(ExceptionLogPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public ExceptionLogDaoImpl() {
        super(ExceptionLogPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_EXCEPTION_LOG).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
