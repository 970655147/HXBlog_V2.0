package com.hx.blog_v2.domain.mapper.blog;

import com.hx.blog_v2.domain.POVOTransferUtils;
import com.hx.blog_v2.domain.po.blog.BlogPO;
import com.hx.blog_v2.domain.vo.blog.AdminBlogVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.mongo.util.ResultSet2MapAdapter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * AdminBlogVOExtractor
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/21/2017 8:42 PM
 */
public class AdminBlogVOExtractor implements ResultSetExtractor<AdminBlogVO> {

    @Override
    public AdminBlogVO extractData(ResultSet rs) throws SQLException, DataAccessException {
        return extractBean(rs);
    }

    /**
     * 从给定的 rs 中 提取结果集
     *
     * @param rs rs
     * @return com.hx.blog_v2.domain.vo.blog.AdminBlogVO
     * @author Jerry.X.He
     * @date 5/21/2017 9:14 PM
     * @since 1.0
     */
    public static AdminBlogVO extractBean(ResultSet rs) throws SQLException, DataAccessException {
        BlogPO po = new BlogPO();
        po.loadFromJSON(new ResultSet2MapAdapter(rs), BlogConstants.LOAD_ALL_CONFIG);
        AdminBlogVO vo = POVOTransferUtils.blogPO2AdminBlogVO(po);
        String tagIds = rs.getString("tagIds");
        String[] tagIdArr = tagIds.split(",");
        if (tagIdArr.length > 0) {
            vo.setBlogTagIds(Arrays.asList(tagIdArr));
        }
        return vo;
    }

}
