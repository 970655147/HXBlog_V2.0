package com.hx.blog_v2.domain.mapper.message;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.message.MessagePO;
import com.hx.blog_v2.domain.vo.message.MessageVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MessageVOMapper
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class MessageVOMapper implements RowMapper<MessageVO> {

    @Override
    public MessageVO mapRow(ResultSet resultSet, int i) throws SQLException {
        MessagePO po = new MessagePO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.LOAD_ALL_CONFIG);
        MessageVO vo = POVOTransferUtils.messagePO2MessageVO(po);
        return vo;
    }

}
