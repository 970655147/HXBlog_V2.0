package com.hx.blog_v2.domain.mapper.rlt;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.resources.RolePO;
import com.hx.blog_v2.domain.vo.rlt.RoleResourceVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RoleResourceVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class RoleResourceVOMapper implements RowMapper<RoleResourceVO> {

    @Override
    public RoleResourceVO mapRow(ResultSet resultSet, int i) throws SQLException {
        RolePO po = new RolePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        RoleResourceVO vo = POVOTransferUtils.rolePO2RoleResourceVO(po);
        return vo;
    }

}
