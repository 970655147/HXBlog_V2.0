package com.hx.blog_v2.dao.front_resources;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.AdvDao;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.domain.po.front_resources.AdvPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * AdvDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:43 PM
 */
@Repository
public class AdvDaoImpl extends BaseDaoImpl<AdvPO> implements AdvDao {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AdvDaoImpl() {
        super(AdvPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableAdv;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
