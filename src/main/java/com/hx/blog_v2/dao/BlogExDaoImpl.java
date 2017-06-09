package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.po.BlogVisitLogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.config.interf.DbConfig;
import com.hx.mongo.connection.interf.ConnectionProvider;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.MultiCriteriaQueryCriteria;
import com.hx.mongo.dao.MysqlBaseDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;

/**
 * BlogExDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogExDaoImpl extends BaseDaoImpl<BlogExPO> implements BlogExDao {

    @Autowired
    private CacheContext cacheContext;

    public BlogExDaoImpl() {
        super(BlogExPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                new MyMysqlConnectionProvider());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogEx;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public BlogExPO get(BeanIdForm params) {
        BlogExPO po = cacheContext.getBlogEx(params.getId());
        if(po != null) {
            return po;
        }

        try {
            po = findOne(Criteria.eq("blog_id", params.getId()), BlogConstants.LOAD_ALL_CONFIG);
            return po;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(BlogExPO po) {
        try {
            insertOne(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
