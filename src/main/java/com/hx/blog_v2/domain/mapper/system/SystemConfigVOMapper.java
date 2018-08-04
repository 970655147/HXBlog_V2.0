package com.hx.blog_v2.domain.mapper.system;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.system.SystemConfigPO;
import com.hx.blog_v2.domain.vo.system.SystemConfigVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SystemConfigVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class SystemConfigVOMapper implements RowMapper<SystemConfigVO> {

    @Override
    public SystemConfigVO mapRow(ResultSet resultSet, int i) throws SQLException {
        SystemConfigPO po = new SystemConfigPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        SystemConfigVO vo = POVOTransferUtils.systemConfigPO2SystemConfigVO(po);
        return vo;
    }

}
