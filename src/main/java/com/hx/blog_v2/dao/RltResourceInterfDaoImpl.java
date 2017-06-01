package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.RltResourceInterfDao;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.po.RltResourceInterfPO;
import com.hx.blog_v2.domain.po.RltRoleResourcePO;
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
public class RltResourceInterfDaoImpl extends MysqlBaseDaoImpl<RltResourceInterfPO> implements RltResourceInterfDao {

    public RltResourceInterfDaoImpl(RltResourceInterfPO bean) {
        super(bean);
    }

    public RltResourceInterfDaoImpl(RltResourceInterfPO bean, DbConfig config) {
        super(bean, config);
    }

    public RltResourceInterfDaoImpl(RltResourceInterfPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public RltResourceInterfDaoImpl(RltResourceInterfPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public RltResourceInterfDaoImpl() {
        super(RltResourceInterfPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_RESOURCE_INTERF).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
