package com.hx.blog_v2.domain.mapper.common;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 从结果集中获取一个字符串的 Mapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 10:01 PM
 */
public class OneStringMapper implements RowMapper<String> {

    /**
     * 需要获取的列的名称
     */
    private String colName;

    public OneStringMapper(String colName) {
        this.colName = colName;
    }

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(colName);
    }
}
