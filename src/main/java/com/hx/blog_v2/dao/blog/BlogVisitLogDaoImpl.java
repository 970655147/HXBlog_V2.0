package com.hx.blog_v2.dao.blog;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogVisitLogDao;
import com.hx.blog_v2.domain.form.blog.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.blog.BlogVisitLogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.MultiCriteriaQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * BlogVisitLogDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogVisitLogDaoImpl extends BaseDaoImpl<BlogVisitLogPO> implements BlogVisitLogDao {

    @Autowired
    private CacheContext cacheContext;

    public BlogVisitLogDaoImpl() {
        super(BlogVisitLogPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogVisitLog;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public BlogVisitLogPO get(BlogVisitLogForm params) {
        BlogVisitLogPO po = cacheContext.getBlogVisitLog(params);
        if (po != null) {
            return po;
        }

        MultiCriteriaQueryCriteria query = Criteria.and(Criteria.eq("blog_id", params.getBlogId()))
                .add(Criteria.eq("request_ip", params.getRequestIp()));
        if (!Tools.isEmpty(params.getCreatedAtDay())) {
            query.add(Criteria.eq("created_at_day", params.getCreatedAtDay()));
        }

        try {
            po = findOne(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(po != null) {
            cacheContext.putBlogVisitLog(params, po);
            return po;
        }
        return null;
    }

}
