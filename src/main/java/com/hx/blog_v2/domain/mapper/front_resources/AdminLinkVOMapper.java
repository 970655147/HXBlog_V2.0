package com.hx.blog_v2.domain.mapper.front_resources;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.front_resources.LinkPO;
import com.hx.blog_v2.domain.vo.front_resources.AdminLinkVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminLinkVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class AdminLinkVOMapper implements RowMapper<AdminLinkVO> {

    @Override
    public AdminLinkVO mapRow(ResultSet resultSet, int i) throws SQLException {
        LinkPO po = new LinkPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        AdminLinkVO vo = POVOTransferUtils.linkPO2AdminLinkVO(po);
        return vo;
    }

}
