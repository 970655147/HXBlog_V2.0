package com.hx.blog_v2.domain.mapper.system;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.system.ExceptionLogPO;
import com.hx.blog_v2.domain.vo.system.ExceptionLogVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ExceptionLogVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class ExceptionLogVOMapper implements RowMapper<ExceptionLogVO> {

    @Override
    public ExceptionLogVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ExceptionLogPO po = new ExceptionLogPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        ExceptionLogVO vo = POVOTransferUtils.exceptionLogPO2ExceptionLogVO(po);
        return vo;
    }

}
