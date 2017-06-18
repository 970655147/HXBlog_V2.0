package com.hx.blog_v2.dao;

import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogTagDao;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.po.BlogTagPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.mongo.config.MysqlDbConfig;
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
public class BlogTagDaoImpl extends BaseDaoImpl<BlogTagPO> implements BlogTagDao {

    @Autowired
    private CacheContext cacheContext;

    public BlogTagDaoImpl() {
        super(BlogTagPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                new MyMysqlConnectionProvider());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogTag;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(BeanIdForm params) {
        BlogTagPO po = cacheContext.blogTag(params.getId());
        if (po != null) {
            return ResultUtils.success(po);
        }

        return ResultUtils.failed("记录不存在 !");
    }
}
