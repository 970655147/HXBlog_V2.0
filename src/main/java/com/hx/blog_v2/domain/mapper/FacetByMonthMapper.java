package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.vo.FacetByMonthVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * FacetByMonthMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 9:56 PM
 */
public class FacetByMonthMapper implements RowMapper<FacetByMonthVO> {

    @Override
    public FacetByMonthVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        FacetByMonthVO vo = new FacetByMonthVO();
        vo.setMonth(rs.getString("month"));
        vo.setCnt(rs.getString("cnt"));
        return vo;
    }
}
