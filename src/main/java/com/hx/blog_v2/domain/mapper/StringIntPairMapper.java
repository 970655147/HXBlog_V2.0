package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.dto.StringIntPair;
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
public class StringIntPairMapper implements RowMapper<StringIntPair> {

    private String leftKey;
    private String rightKey;

    public StringIntPairMapper(String leftKey, String rightKey) {
        this.leftKey = leftKey;
        this.rightKey = rightKey;
    }

    @Override
    public StringIntPair mapRow(ResultSet rs, int rowNum) throws SQLException {
        StringIntPair result = new StringIntPair();
        result.setLeft(rs.getString(leftKey));
        result.setRight(rs.getInt(rightKey));
        return result;
    }

}
