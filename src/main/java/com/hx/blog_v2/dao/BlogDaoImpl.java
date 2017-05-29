package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

/**
 * BlogDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogDaoImpl extends MysqlBaseDaoImpl<BlogPO> implements BlogDao {

    public BlogDaoImpl(BlogPO bean) {
        super(bean);
    }

    public BlogDaoImpl(BlogPO bean, DbConfig config) {
        super(bean, config);
    }

    public BlogDaoImpl(BlogPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BlogDaoImpl(BlogPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BlogDaoImpl() {
        super(BlogPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_BLOG).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
