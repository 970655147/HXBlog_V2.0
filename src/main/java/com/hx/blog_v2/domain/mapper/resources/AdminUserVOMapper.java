package com.hx.blog_v2.domain.mapper.resources;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.resources.UserPO;
import com.hx.blog_v2.domain.vo.resources.AdminUserVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminUserVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class AdminUserVOMapper implements RowMapper<AdminUserVO> {

    @Override
    public AdminUserVO mapRow(ResultSet resultSet, int i) throws SQLException {
        UserPO po = new UserPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        AdminUserVO vo = POVOTransferUtils.userPO2AdminUserVO(po);
        return vo;
    }

}
