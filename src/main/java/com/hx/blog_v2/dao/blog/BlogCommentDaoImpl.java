package com.hx.blog_v2.dao.blog;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.dao.interf.BaseDaoImpl;
import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.domain.form.common.BeanIdForm;
import com.hx.blog_v2.domain.mapper.blog.CommentVOMapper;
import com.hx.blog_v2.domain.mapper.common.OneIntMapper;
import com.hx.blog_v2.domain.po.blog.BlogCommentPO;
import com.hx.blog_v2.domain.vo.blog.CommentVO;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.MyMysqlConnectionProvider;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.blog_v2.util.SqlUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import com.hx.mongo.config.MysqlDbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * BlogCommentDaoImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:38 AM
 */
@Repository
public class BlogCommentDaoImpl extends BaseDaoImpl<BlogCommentPO> implements BlogCommentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    public BlogCommentDaoImpl() {
        super(BlogCommentPO.PROTO_BEAN,
                new MysqlDbConfig(BlogConstants.MYSQL_DB_CONFIG).table(tableName()).id(id()),
                MyMysqlConnectionProvider.getInstance());
    }


    public static String tableName() {
        return BlogConstants.getInstance().tableBlogComment;
    }

    public static String id() {
        return BlogConstants.getInstance().tableId;
    }

    @Override
    public Result getCommentFor(BeanIdForm params, Page<?> page) {
        String pageNow = String.valueOf(page.getPageNow());
        List<List<CommentVO>> result = cacheContext.getComment(params.getId(), pageNow);
        if (result != null) {
            return ResultUtils.success(result);
        }

        // 1, 2 可以折叠, 可惜 我的 mysql 似乎 是不支持 limit 作为子查询
        String selectSql = " select * from blog_comment where blog_id = ? and deleted = 0 and floor_id in ( %s ) order by created_at asc ";
        String selectFloorSql = " select distinct(floor_id) as floor_id from blog_comment where blog_id = ? and deleted = 0 order by floor_id asc limit ?, ? ";

        List<Integer> floorIds = jdbcTemplate.query(selectFloorSql, new Object[]{params.getId(), page.recordOffset(), page.getPageSize()}, new OneIntMapper("floor_id"));
        List<CommentVO> comments = Collections.emptyList();
        if (!Tools.isEmpty(floorIds)) {
            Object[] sqlParams = new Object[]{params.getId()};
            comments = jdbcTemplate.query(String.format(selectSql, SqlUtils.wrapInSnippetForIds(floorIds)), sqlParams, new CommentVOMapper());
        }
        result = generateCommentTree(comments);
        cacheContext.putComment(params.getId(), pageNow, result);
        return ResultUtils.success(result);
    }

    /**
     * 生成评论树
     *
     * @param comments comments
     * @return com.hx.json.JSONArray
     * @author Jerry.X.He
     * @date 5/28/2017 2:48 PM
     * @since 1.0
     */
    private List<List<CommentVO>> generateCommentTree(List<CommentVO> comments) {
        Map<String, List<CommentVO>> commentsByFloor = new LinkedHashMap<>();
        for (CommentVO comment : comments) {
            List<CommentVO> floorComments = commentsByFloor.get(comment.getFloorId());
            if (floorComments == null) {
                floorComments = new ArrayList<>();
                commentsByFloor.put(comment.getFloorId(), floorComments);
            }

            floorComments.add(comment);
        }

        List<List<CommentVO>> result = new ArrayList<>(commentsByFloor.size());
        for (Map.Entry<String, List<CommentVO>> entry : commentsByFloor.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

}
