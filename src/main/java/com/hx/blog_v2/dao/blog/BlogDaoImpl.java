package com.hx.blog_v2.dao.blog;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.mapper.common.OneStringMapper;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * BlogDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogDaoImpl extends BaseDaoImpl<BlogPO> implements BlogDao {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BlogDaoImpl() {
        super(BlogPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlog;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(BeanIdForm params) {
        BlogPO po = cacheContext.getBlog(params.getId());
        if (po != null) {
            return ResultUtils.success(po);
        }

        IQueryCriteria query = Criteria.and(Criteria.eq("id", params.getId()), Criteria.eq("deleted", 0));
        try {
            po = findOne(query, BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        if (po != null) {
            cacheContext.putBlog(po.getId(), po);
            return ResultUtils.success(po);
        }
        return null;
    }

    @Override
    public Result getTagIdsFor(BeanIdForm params) {
        List<String> tagIds = cacheContext.getTagIds(params.getId());
        if (tagIds != null) {
            return ResultUtils.success(tagIds);
        }

        try {
            String sql = " select rlt.tag_id from rlt_blog_tag as rlt where rlt.blog_id = ? ";
            tagIds = jdbcTemplate.query(sql, new Object[]{params.getId()}, new OneStringMapper("tag_id"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        if (!Tools.isEmpty(tagIds)) {
            cacheContext.putTagIds(params.getId(), tagIds);
            return ResultUtils.success(tagIds);
        }
        return ResultUtils.failed(" 没有对应的标签记录 ");
    }
}
