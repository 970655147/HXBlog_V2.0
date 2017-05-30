package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
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
public class RltRoleResourceDaoImpl extends MysqlBaseDaoImpl<RltRoleResourcePO> implements RltRoleResourceDao {

    public RltRoleResourceDaoImpl(RltRoleResourcePO bean) {
        super(bean);
    }

    public RltRoleResourceDaoImpl(RltRoleResourcePO bean, DbConfig config) {
        super(bean, config);
    }

    public RltRoleResourceDaoImpl(RltRoleResourcePO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public RltRoleResourceDaoImpl(RltRoleResourcePO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public RltRoleResourceDaoImpl() {
        super(RltRoleResourcePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_ROLE_RESOURCE).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
