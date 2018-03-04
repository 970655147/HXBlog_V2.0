package com.hx.blog_v2.domain.mapper.front_resources;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.front_resources.ImagePO;
import com.hx.blog_v2.domain.vo.front_resources.ImageVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ImageVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class ImageVOMapper implements RowMapper<ImageVO> {

    @Override
    public ImageVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ImagePO po = new ImagePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        ImageVO vo = POVOTransferUtils.imagePO2ImageVO(po);
        return vo;
    }

}
