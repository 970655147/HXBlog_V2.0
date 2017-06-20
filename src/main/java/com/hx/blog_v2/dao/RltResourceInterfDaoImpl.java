package com.hx.blog_v2.dao;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.RltResourceInterfDao;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.blog_v2.domain.po.RltResourceInterfPO;
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
public class RltResourceInterfDaoImpl extends BaseDaoImpl<RltResourceInterfPO> implements RltResourceInterfDao {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RltResourceInterfDaoImpl() {
        super(RltResourceInterfPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableRltResourceInterf;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result getInterfsByResourceIds(BeanIdsForm params) {
        String resourceIds = params.getIds();
        List<String> interfs = cacheContext.interfsByResourceIds(resourceIds);
        if (interfs == null) {
            String resourceIdSql = " select i.name as interf_name from rlt_resource_interf as ri inner join interf as i on ri.interf_id = i.id " +
                    " where i.enable = 1 and i.deleted = 0 and resource_id in ( %s ) order by i.sort ";
            String inSnippet = resourceIds;
            inSnippet = inSnippet.substring(inSnippet.indexOf("[") + 1, inSnippet.lastIndexOf("]"));
            interfs = jdbcTemplate.query(String.format(resourceIdSql, inSnippet), new OneStringMapper("interf_name"));
            cacheContext.putInterfsByResourceIds(resourceIds, interfs);
        }

        return ResultUtils.success(interfs);
    }

}
