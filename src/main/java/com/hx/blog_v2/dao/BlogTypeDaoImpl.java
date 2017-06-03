package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogTypeDao;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.domain.po.BlogTypePO;
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
public class BlogTypeDaoImpl extends BaseDaoImpl<BlogTypePO> implements BlogTypeDao {

    public BlogTypeDaoImpl() {
        super(BlogTypePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                new MyMysqlConnectionProvider());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogType;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
