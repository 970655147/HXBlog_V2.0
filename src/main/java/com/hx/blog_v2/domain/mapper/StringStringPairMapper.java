package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.dto.StringStringPair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * StringIntPairMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:39 PM
 */
public class StringStringPairMapper implements RowMapper<StringStringPair> {

    private String leftKey;
    private String rightKey;

    public StringStringPairMapper(String leftKey, String rightKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    @Override
    public StringStringPair mapRow(ResultSet rs, int rowNum) throws SQLException {
        StringStringPair result = new StringStringPair();
        result.setLeft(rs.getString(leftKey));
        result.setRight(rs.getString(rightKey));
        return result;
    }

}
