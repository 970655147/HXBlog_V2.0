package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.InterfDao;
import com.hx.blog_v2.dao.interf.RoleDao;
import com.hx.blog_v2.domain.po.InterfPO;
import com.hx.blog_v2.domain.po.RolePO;
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
public class InterfDaoImpl extends MysqlBaseDaoImpl<InterfPO> implements InterfDao {

    public InterfDaoImpl(InterfPO bean) {
        super(bean);
    }

    public InterfDaoImpl(InterfPO bean, DbConfig config) {
        super(bean, config);
    }

    public InterfDaoImpl(InterfPO bean, ConnectionProvider<Connection> connectionProvider) {
        super(bean, connectionProvider);
    }

    public InterfDaoImpl(InterfPO bean, DbConfig config, ConnectionProvider<Connection> connectionProvider) {
        super(bean, config, connectionProvider);
    }

    public InterfDaoImpl() {
        super(InterfPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(BlogConstants.TABLE_INTERF).id(BlogConstants.TABLE_ID),
                new MyMysqlConnectionProvider()
        );
    }


}
