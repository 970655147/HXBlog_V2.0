package com.hx.blog_v2.domain.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 从结果集中获取一个整数的 Mapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 10:01 PM
 */
public class OneIntMapper implements RowMapper<Integer> {

    /**
     * 需要获取的列的名称
     */
    private String colName;

    public OneIntMapper(String colName) {
        this.colName = colName;
    }

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt(colName);
    }
}
