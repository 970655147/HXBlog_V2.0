package com.hx.blog_v2.dao.front_resources;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.MoodDao;
import com.hx.blog_v2.domain.po.front_resources.MoodPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.stereotype.Repository;

/**
 * MoodDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class MoodDaoImpl extends BaseDaoImpl<MoodPO> implements MoodDao {

    public MoodDaoImpl() {
        super(MoodPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableMood;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
