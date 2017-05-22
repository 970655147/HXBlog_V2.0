package com.hx.blog_v2.domain.mapper;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.MoodPO;
import com.hx.blog_v2.domain.vo.AdminMoodVO;
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
public class AdminMoodVOMapper implements RowMapper<AdminMoodVO> {

    @Override
    public AdminMoodVO mapRow(ResultSet resultSet, int i) throws SQLException {
        MoodPO po = new MoodPO();
        po.loadFromJSON(new ResultSet2MapAdapter(resultSet), BlogConstants.IDX_MANAGER.getDoLoad());
        AdminMoodVO vo = POVOTransferUtils.moodPO2AdminMoodVO(po);
        return vo;
    }

}
