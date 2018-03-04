package com.hx.blog_v2.domain.mapper.blog;

import com.hx.blog_v2.domain.vo.blog.AdminBlogVO;
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
public class AdminBlogVOMapper implements RowMapper<AdminBlogVO> {

    @Override
    public AdminBlogVO mapRow(ResultSet resultSet, int i) throws SQLException {
        return AdminBlogVOExtractor.extractBean(resultSet);
    }

}
