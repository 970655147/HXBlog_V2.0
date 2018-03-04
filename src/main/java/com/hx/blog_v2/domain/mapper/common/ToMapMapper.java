package com.hx.blog_v2.domain.mapper.common;

import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * ToMapMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/10/2017 11:26 PM
 */
public class ToMapMapper implements RowMapper<Map<String, Object>> {

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ResultSet2MapAdapter(rs);
    }
}
