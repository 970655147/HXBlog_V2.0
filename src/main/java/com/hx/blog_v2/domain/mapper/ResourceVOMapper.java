package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.ResourcePO;
import com.hx.blog_v2.domain.vo.ResourceVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.log.util.Tools;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * BlogVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/27/2017 9:35 PM
 */
public class ResourceVOMapper implements RowMapper<ResourceVO> {

    @Override
    public ResourceVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ResourcePO po = new ResourcePO();
        Map<String, Object> resultMap = new ResultSet2MapAdapter(resultSet);
        po.loadFromJSON(resultMap, BlogConstants.LOAD_ALL_CONFIG);
        if(Tools.NULL.equals(po.getParentId())) {
            po.setParentId(null);
        }

        ResourceVO vo = POVOTransferUtils.resourcePO2ResourceVO(po);
        return vo;
    }

}
