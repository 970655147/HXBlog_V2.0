package com.hx.blog_v2.dao;

import com.hx.blog_v2.context.SpringContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.UserDao;
import com.hx.blog_v2.domain.po.UserPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
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
public class UserDaoImpl extends BaseDaoImpl<UserPO> implements UserDao {

    public UserDaoImpl() {
        super(UserPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableUser;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
