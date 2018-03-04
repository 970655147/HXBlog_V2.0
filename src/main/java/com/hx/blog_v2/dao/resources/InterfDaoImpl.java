package com.hx.blog_v2.dao.resources;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.InterfDao;
import com.hx.blog_v2.domain.po.resources.InterfPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.stereotype.Repository;

/**
 * InterfDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class InterfDaoImpl extends BaseDaoImpl<InterfPO> implements InterfDao {

    public InterfDaoImpl() {
        super(InterfPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableInterf;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
