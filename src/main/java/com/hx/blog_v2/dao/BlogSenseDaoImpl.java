package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogSenseDao;
import com.hx.blog_v2.domain.form.BlogSenseForm;
import com.hx.blog_v2.domain.po.BlogSensePO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.MultiCriteriaQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * BlogTagDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogSenseDaoImpl extends BaseDaoImpl<BlogSensePO> implements BlogSenseDao {

    @Autowired
    private CacheContext cacheContext;

    public BlogSenseDaoImpl() {
        super(BlogSensePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                new MyMysqlConnectionProvider());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogSense;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(BlogSenseForm params) {
        BlogSensePO po = cacheContext.getBlogSense(params);
        if (po != null) {
            return ResultUtils.success(po);
        }

        MultiCriteriaQueryCriteria query = Criteria.and(Criteria.eq("blog_id", params.getBlogId()))
                .add(Criteria.eq("name", params.getName())).add(Criteria.eq("request_ip", params.getRequestIp()))
                .add(Criteria.eq("sense", params.getSense()));
        try {
            po = findOne(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        if (po != null) {
            return ResultUtils.success(po);
        }
        return ResultUtils.failed("没有对应的记录 !");
    }


}
