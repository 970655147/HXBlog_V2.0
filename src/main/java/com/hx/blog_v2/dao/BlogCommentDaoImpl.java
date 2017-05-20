package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.domain.po.BlogCommentPO;
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
public class BlogCommentDaoImpl extends MysqlBaseDaoImpl<BlogCommentPO, Integer> implements BlogCommentDao {

    public BlogCommentDaoImpl(BlogCommentPO bean) {
        super(bean);
    }

    public BlogCommentDaoImpl(BlogCommentPO bean, DbConfig config) {
        super(bean, config);
    }

    public BlogCommentDaoImpl(BlogCommentPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public BlogCommentDaoImpl(BlogCommentPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public BlogCommentDaoImpl() {
        super(BlogCommentPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_BLOG_COMMENT).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
