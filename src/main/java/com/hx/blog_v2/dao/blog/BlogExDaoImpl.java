package com.hx.blog_v2.dao.blog;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.po.blog.BlogExPO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


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
                MyMysqlConnectionProvider.getInstance());
    }

    public static String tableName() {
        return BlogConstants.getInstance().tableBlogEx;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(BeanIdForm params) {
        BlogExPO po = cacheContext.getBlogEx(params.getId());
        if (po != null) {
            return ResultUtils.success(po);
        }

        try {
            po = findOne(Criteria.eq("blog_id", params.getId()), BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }

        if (po != null) {
            cacheContext.putBlogEx(po);
            return ResultUtils.success(po);
        }
        return ResultUtils.failed("记录不存在 !");
    }


}
