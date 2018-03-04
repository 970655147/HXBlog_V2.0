package com.hx.blog_v2.domain.mapper.resources;

import com.hx.blog_v2.domain.vo.common.Id2NameVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Id2NameRoleMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 2:57 PM
 */
public class Id2NameRoleMapper implements RowMapper<Id2NameVO> {

    /**
     * id, name 的key
     */
    private String idKey;
    private String nameKey;

    public Id2NameRoleMapper(String idKey, String nameKey) {
        this.idKey = idKey;
        this.nameKey = nameKey;
    }

    public Id2NameRoleMapper() {
        this("id", "name");
    }

    @Override
    public Id2NameVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        Id2NameVO vo = new Id2NameVO();
        vo.setId(rs.getString(idKey));
        vo.setName(rs.getString(nameKey));
        return vo;
    }
}
