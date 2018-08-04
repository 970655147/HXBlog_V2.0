package com.hx.blog_v2.dao.resources;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.RoleDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.po.resources.RolePO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * RoleDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<RolePO> implements RoleDao {

    @Autowired
    private CacheContext cacheContext;

    public RoleDaoImpl() {
        super(RolePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableRole;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result get(BeanIdForm params) {
        RolePO po = cacheContext.role(params.getId());
        if (po != null) {
            return ResultUtils.success(po);
        }

        return ResultUtils.failed("记录不存在 !");
    }


}
