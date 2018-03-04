package com.hx.blog_v2.domain.mapper.rlt;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.resources.UserPO;
import com.hx.blog_v2.domain.vo.rlt.UserRoleVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserRoleVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class UserRoleVOMapper implements RowMapper<UserRoleVO> {

    @Override
    public UserRoleVO mapRow(ResultSet resultSet, int i) throws SQLException {
        UserPO po = new UserPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        UserRoleVO vo = POVOTransferUtils.userPO2UserRoleVO(po);
        return vo;
    }

}
