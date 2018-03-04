package com.hx.blog_v2.dao.rlt;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.RltBlogTagDao;
import com.hx.blog_v2.domain.po.rlt.RltBlogTagPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.stereotype.Repository;

/**
 * RltBlogTagDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class RltBlogTagDaoImpl extends BaseDaoImpl<RltBlogTagPO> implements RltBlogTagDao {

    public RltBlogTagDaoImpl() {
        super(RltBlogTagPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableRltBlogTag;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }


}
