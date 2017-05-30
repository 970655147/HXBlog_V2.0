package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.po.RltUserRoleRolePO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminBlogVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class RltUserRolePOMapper implements RowMapper<RltUserRoleRolePO> {

    @Override
    public RltUserRoleRolePO mapRow(ResultSet resultSet, int i) throws SQLException {
        RltUserRoleRolePO po = new RltUserRoleRolePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        return po;
    }

}
