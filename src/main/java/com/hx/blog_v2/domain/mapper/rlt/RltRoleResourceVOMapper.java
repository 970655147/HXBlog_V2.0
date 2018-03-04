package com.hx.blog_v2.domain.mapper.rlt;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.rlt.RltRoleResourcePO;
import com.hx.blog_v2.domain.vo.rlt.RltRoleResourceVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RltRoleResourceVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class RltRoleResourceVOMapper implements RowMapper<RltRoleResourceVO> {

    @Override
    public RltRoleResourceVO mapRow(ResultSet resultSet, int i) throws SQLException {
        RltRoleResourcePO po = new RltRoleResourcePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        RltRoleResourceVO vo = POVOTransferUtils.rltRoleResourcePO2RltRoleResourceVO(po);
        vo.setResourceName(resultSet.getString("resource_name"));
        return vo;
    }

}
