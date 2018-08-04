package com.hx.blog_v2.domain.mapper.front_resources;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.front_resources.ImagePO;
import com.hx.blog_v2.domain.vo.front_resources.AdminImageVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminImageVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class AdminImageVOMapper implements RowMapper<AdminImageVO> {

    @Override
    public AdminImageVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ImagePO po = new ImagePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        AdminImageVO vo = POVOTransferUtils.imagePO2AdminImageVO(po);
        return vo;
    }

}
