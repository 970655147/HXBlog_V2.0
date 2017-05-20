package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

/**
 * BlogExDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogExDaoImpl extends MysqlBaseDaoImpl<BlogExPO, Integer> implements BlogExDao {

    public BlogExDaoImpl(BlogExPO bean) {
        super(bean);
    }

    public BlogExDaoImpl(BlogExPO bean, DbConfig config) {
        super(bean, config);
    }

    public BlogExDaoImpl(BlogExPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BlogExDaoImpl(BlogExPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BlogExDaoImpl() {
        super(BlogExPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_BLOG_EX).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
