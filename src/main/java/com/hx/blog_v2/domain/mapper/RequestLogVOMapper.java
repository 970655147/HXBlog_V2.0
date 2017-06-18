package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.RequestLogPO;
import com.hx.blog_v2.domain.vo.RequestLogVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminBlogVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class RequestLogVOMapper implements RowMapper<RequestLogVO> {

    @Override
    public RequestLogVO mapRow(ResultSet resultSet, int i) throws SQLException {
        RequestLogPO po = new RequestLogPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        RequestLogVO vo = POVOTransferUtils.requestLogPO2RequestLogVO(po);
        return vo;
    }

}
