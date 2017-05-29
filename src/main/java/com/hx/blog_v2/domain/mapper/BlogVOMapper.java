package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.BlogExPO;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.util.BlogConstants;
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
public class BlogVOMapper implements RowMapper<BlogVO> {

    @Override
    public BlogVO mapRow(ResultSet resultSet, int i) throws SQLException {
        BlogPO po = new BlogPO();
        BlogExPO exPo = new BlogExPO();

        Map<String, Object> resultMap = new ResultSet2MapAdapter(resultSet);
        po.loadFromJSON(resultMap, BlogConstants.LOAD_ALL_CONFIG);
        exPo.loadFromJSON(resultMap, BlogConstants.LOAD_ALL_CONFIG);

        BlogVO vo = POVOTransferUtils.blogPO2BlogVO(po);
        vo = POVOTransferUtils.blogExPO2BlogVO(exPo, vo);
        return vo;
    }

}
