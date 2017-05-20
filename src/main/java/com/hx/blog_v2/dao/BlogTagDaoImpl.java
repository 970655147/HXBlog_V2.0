package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.domain.po.BlogTagPO;
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
public class BlogTagDaoImpl extends MysqlBaseDaoImpl<BlogTagPO, Integer> implements BlogTagDao {

    public BlogTagDaoImpl(BlogTagPO bean) {
        super(bean);
    }

    public BlogTagDaoImpl(BlogTagPO bean, DbConfig config) {
        super(bean, config);
    }

    public BlogTagDaoImpl(BlogTagPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BlogTagDaoImpl(BlogTagPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BlogTagDaoImpl() {
        super(BlogTagPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_BLOG_TAG).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
