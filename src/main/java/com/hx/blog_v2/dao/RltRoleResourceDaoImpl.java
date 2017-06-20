package com.hx.blog_v2.dao;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.RltRoleResourceDao;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.blog_v2.domain.po.RltRoleResourcePO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.mongo.config.MysqlDbConfig;
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
public class RltRoleResourceDaoImpl extends BaseDaoImpl<RltRoleResourcePO> implements RltRoleResourceDao {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RltRoleResourceDaoImpl() {
        super(RltRoleResourcePO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableRltRoleResource;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result getResourceIdsByRoleIds(BeanIdsForm params) {
        String roleIds = params.getIds();
        List<String> resourceIds = cacheContext.resourceIdsByRoleIds(roleIds);
        if (resourceIds == null) {
            String resourceIdSql = " select resource_id from rlt_role_resource as rr inner join resource as r on rr.resource_id = r.id " +
                    " where r.deleted = 0 and role_id in ( %s ) order by r.sort ";
            String inSnippet = roleIds;
            inSnippet = inSnippet.substring(1, inSnippet.length() - 1);
            resourceIds = jdbcTemplate.query(String.format(resourceIdSql, inSnippet), new OneStringMapper("resource_id"));
            cacheContext.putResourceIdsByRoleIds(roleIds, resourceIds);
        }

        return ResultUtils.success(resourceIds);
    }
}
