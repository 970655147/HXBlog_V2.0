package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.mapper.AdminCommentVOMapper;
import com.hx.blog_v2.domain.mapper.BlogVOMapper;
import com.hx.blog_v2.domain.mapper.CommentVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.BlogCommentPO;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.BlogVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class BlogCommentServiceImpl extends BaseServiceImpl<BlogCommentPO> implements BlogCommentService {

    @Autowired
    private BlogCommentDao commentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result add(CommentSaveForm params) {
        BlogVO blog = null;
        try {
            String selectAuthorSql = " select * from blog where deleted = 0 and id = ? ";
            blog = jdbcTemplate.queryForObject(selectAuthorSql, new Object[]{params.getBlogId()}, new BlogVOMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (blog == null) {
            return ResultUtils.failed("没有对应的博客 !");
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        BlogCommentPO po = new BlogCommentPO(user.getUserName(), user.getEmail(), user.getHeadImgUrl(), params.getToUser(),
                user.getTitle(), params.getComment());
        po.setBlogId(params.getBlogId());
        int endOfReply = idxOfEndRe(params.getComment());
        if ((!Tools.isEmpty(params.getFloorId())) && (endOfReply >= 0)) {
            BlogCommentPO replyTo = get0(params.getBlogId(), params.getFloorId(), params.getCommentId());
            if ((replyTo == null) || (!params.getToUser().equalsIgnoreCase(replyTo.getName()))) {
                return ResultUtils.failed("没有对应的回复 !");
            }

            po.setFloorId(params.getFloorId());
            po.setComment(po.getComment().substring(endOfReply).trim());
            po.setParentCommentId(replyTo.getId());
        } else {
            po.setFloorId(cacheContext.nextFloorId(po.getBlogId()));
            po.setToUser(blog.getAuthor());
        }
        po.setCommentId(cacheContext.nextCommentId(po.getBlogId(), po.getFloorId()));

        try {
            commentDao.save(po, BlogConstants.ADD_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result adminList(AdminCommentSearchForm params, Page<AdminCommentVO> page) {
        String selectSql = " select c.*, b.title as blog_name from blog_comment as c " +
                " inner join blog as b on c.blog_id = b.id where c.deleted = 0 ";
        String selectSuffix = String.format(" order by %s %s ",
                AdminCommentSearchForm.SORT_2_FIELD.get(params.getSort()), params.isAsc() ? " asc " : " desc ")
                + " limit ?, ? ";
        String countSql = " select count(*) as totalRecord from blog_comment as c " +
                " inner join blog as b on c.blog_id = b.id where c.deleted = 0 ";

        StringBuilder condSqlSb = new StringBuilder();
        List<Object> selectParams = new ArrayList<>(10);
        encapQueryForAdminList(params, condSqlSb, selectParams);

        String condSql = condSqlSb.toString();
        Object[] countParams = selectParams.toArray();
        selectParams.add(page.recordOffset());
        selectParams.add(page.getPageSize());

        List<AdminCommentVO> list = jdbcTemplate.query(selectSql + condSql + selectSuffix , selectParams.toArray(), new AdminCommentVOMapper());
        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        page.setList(list);
        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result floorCommentList(FloorCommentListSearchForm params, Page<CommentVO> page) {
        StringBuilder sql = new StringBuilder(" select * from blog_comment as c where c.deleted = 0 ");
        List<Object> sqlParams = new ArrayList<>(4);
        if (!Tools.isEmpty(params.getBlogId())) {
            sql.append(" and c.blog_id = ? ");
            sqlParams.add(params.getBlogId());
        }
        if (!Tools.isEmpty(params.getFloorId())) {
            sql.append(" and c.floor_id = ? ");
            sqlParams.add(params.getFloorId());
        }
        sql.append(" order by c.comment_id limit ?, ? ");
        sqlParams.add(page.recordOffset());
        sqlParams.add(page.getPageSize());

        List<CommentVO> list = jdbcTemplate.query(sql.toString(), sqlParams.toArray(), new CommentVOMapper());
        page.setList(list);
        return ResultUtils.success(page);
    }

    @Override
    public Result update(CommentSaveForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long modified = commentDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("to_user", params.getToUser()).add("content", params.getComment())
                            .add("updated_at", updatedAt)
            ).getModifiedCount();
            if (modified == 0) {
                return ResultUtils.failed("评论[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(1);
    }

    @Override
    public Result remove(BeanIdForm params) {
        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        try {
            long deleted = commentDao.updateOne(Criteria.eq("id", params.getId()),
                    Criteria.set("deleted", "1").add("updated_at", updatedAt)
            ).getModifiedCount();
            if (deleted == 0) {
                return ResultUtils.failed("评论[" + params.getId() + "]不存在 !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.failed(Tools.errorMsg(e));
        }
        return ResultUtils.success(params.getId());
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 如果给定的评论内容是回复的话, 获取回复的开始索引
     *
     * @param comment comment
     * @return int
     * @author Jerry.X.He
     * @date 6/4/2017 1:49 PM
     * @since 1.0
     */
    private int idxOfEndRe(String comment) {
        if (!comment.startsWith(BlogConstants.REPLY_COMMENT_PREFIX)) {
            return -1;
        }

        return comment.indexOf(BlogConstants.REPLY_COMMENT_SUFFIX) + BlogConstants.REPLY_COMMENT_SUFFIX.length();
    }

    /**
     * 获取 给定的博客, 给定的层数, 给定的评论索引的评论
     *
     * @param blogId    blogId
     * @param floorId   floorId
     * @param commentId commentId
     * @return com.hx.blog_v2.domain.po.BlogCommentPO
     * @author Jerry.X.He
     * @date 6/4/2017 3:18 PM
     * @since 1.0
     */
    private BlogCommentPO get0(String blogId, String floorId, String commentId) {
        try {
            return commentDao.findOne(Criteria.and(Criteria.eq("deleted", "0"))
                            .add(Criteria.eq("blog_id", blogId)).add(Criteria.eq("floor_id", floorId))
                            .add(Criteria.eq("comment_id", commentId)),
                    BlogConstants.LOAD_ALL_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 封装查询评论的查询条件, sql [后台查询]
     *
     * @param params       params
     * @param condSqlSb    condSqlSb
     * @param selectParams selectParams
     * @return void
     * @author Jerry.X.He
     * @date 6/4/2017 5:49 PM
     * @since 1.0
     */
    private void encapQueryForAdminList(AdminCommentSearchForm params, StringBuilder condSqlSb, List<Object> selectParams) {
        if (!Tools.isEmpty(params.getBlogId())) {
            condSqlSb.append(" and b.blog_id = ");
            selectParams.add(params.getBlogId());
        }
        if (!Tools.isEmpty(params.getBlogTypeId())) {
            condSqlSb.append(" and b.blog_type_id = ");
            selectParams.add(params.getBlogTypeId());
        }
        if (!Tools.isEmpty(params.getBlogName())) {
            condSqlSb.append(" and b.name like ? ");
            selectParams.add(SqlUtils.wrapWildcard(params.getBlogTypeId()));
        }
        if (!Tools.isEmpty(params.getUserName())) {
            condSqlSb.append(" and c.name like ? ");
            selectParams.add(SqlUtils.wrapWildcard(params.getUserName()));
        }
        if (!Tools.isEmpty(params.getToUserName())) {
            condSqlSb.append(" and c.to_user like ? ");
            selectParams.add(SqlUtils.wrapWildcard(params.getToUserName()));
        }
        if (!Tools.isEmpty(params.getContent())) {
            condSqlSb.append(" and c.content like ? ");
            selectParams.add(SqlUtils.wrapWildcard(params.getContent()));
        }
        // 如果没有上述三个模糊匹配, 则查询keywords
        if (!Tools.isEmpty(params.getKeywords()) && (selectParams.size() == 0)) {
            condSqlSb.append(" (c.name like ? or c.to_user like ? or c.content like ?) ");
            selectParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
            selectParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
            selectParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
        }
    }

}
