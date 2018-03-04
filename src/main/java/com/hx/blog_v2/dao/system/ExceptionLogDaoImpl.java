package com.hx.blog_v2.dao.system;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.ExceptionLogDao;
import com.hx.blog_v2.domain.po.system.ExceptionLogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.stereotype.Repository;

/**
 * ExceptionLogDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class ExceptionLogDaoImpl extends BaseDaoImpl<ExceptionLogPO> implements ExceptionLogDao {

    public ExceptionLogDaoImpl() {
        super(ExceptionLogPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableExceptionLog;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
