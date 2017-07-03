package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.dao.interf.BlogDao;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.blog_v2.domain.form.BeanIdForm;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.blog_v2.domain.mapper.AdminCommentVOMapper;
import com.hx.blog_v2.domain.mapper.CommentVOMapper;
import com.hx.blog_v2.domain.mapper.OneIntMapper;
import com.hx.blog_v2.domain.po.BlogCommentPO;
import com.hx.blog_v2.domain.po.BlogPO;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.blog_v2.util.*;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

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
    private BlogDao blogDao;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private BlogCommentDao commentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result add(CommentSaveForm params) {
        Result getBlogResult = blogDao.get(new BeanIdForm(params.getBlogId()));
        if (!getBlogResult.isSuccess()) {
            return getBlogResult;
        }

        BlogPO blog = (BlogPO) getBlogResult.getData();
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        BlogCommentPO po = new BlogCommentPO(user.getName(), user.getEmail(), user.getHeadImgUrl(), params.getToUser(),
                user.getTitle(), params.getComment());
        po.setBlogId(params.getBlogId());
        encapCommentPO(blog, po, params);

        Result saveResult = commentDao.add(po);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }
        return ResultUtils.success(po.getId());
    }

    @Override
    public Result list(BeanIdForm params, Page<List<CommentVO>> page) {
        String selectSql = " select * from blog_comment where blog_id = ? and deleted = 0 and floor_id in ( %s ) order by created_at asc ";
        String selectFloorSql = " select distinct(floor_id) as floor_id from blog_comment where blog_id = ? and deleted = 0 order by floor_id asc limit ?, ? ";
        String countSql = " select count(distinct(floor_id)) as totalRecord from blog_comment where blog_id = ? and deleted = 0 ";

        Object[] sqlParams = new Object[]{params.getId()};
        Integer totalRecord = jdbcTemplate.queryForObject(countSql, sqlParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<List<CommentVO>>emptyList());
        } else {
            // 1, 2 可以折叠, 可惜 我的 mysql 似乎 是不支持 limit 作为子查询
            List<Integer> floorIds = jdbcTemplate.query(selectFloorSql, new Object[]{params.getId(), page.recordOffset(), page.getPageSize()}, new OneIntMapper("floor_id"));
            List<CommentVO> comments = Collections.emptyList();
            if (!Tools.isEmpty(floorIds)) {
                comments = jdbcTemplate.query(String.format(selectSql, SqlUtils.wrapInSnippetForIds(floorIds)), sqlParams, new CommentVOMapper());
            }
            List<List<CommentVO>> result = generateCommentTree(comments);
            page.setList(result);
        }

        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
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

        Integer totalRecord = jdbcTemplate.queryForObject(countSql + condSql, countParams, new OneIntMapper("totalRecord"));
        if (totalRecord <= 0) {
            page.setList(Collections.<AdminCommentVO>emptyList());
        } else {
            List<AdminCommentVO> list = jdbcTemplate.query(selectSql + condSql + selectSuffix, selectParams.toArray(), new AdminCommentVOMapper());
            page.setList(list);
        }

        page.setTotalRecord(totalRecord);
        return ResultUtils.success(page);
    }

    @Override
    public Result floorCommentList(FloorCommentListSearchForm params, Page<CommentVO> page) {
        StringBuilder sql = new StringBuilder(" select * from blog_comment as c where c.deleted = 0 ");
        List<Object> sqlParams = new ArrayList<>(4);
        encapFloorCommentList(params, sql, sqlParams);

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
        IQueryCriteria query = Criteria.eq("id", params.getId());
        IUpdateCriteria update = Criteria.set("to_user", params.getToUser()).add("comment", params.getComment())
                .add("updated_at", updatedAt);

        Result result = commentDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(params.getBlogId());
    }

    @Override
    public Result remove(BeanIdForm params) {
        IQueryCriteria query = Criteria.eq("id", params.getId());
        Result getResult = commentDao.get(query);
        if (!getResult.isSuccess()) {
            return getResult;
        }

        String updatedAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        IUpdateCriteria update = Criteria.set("deleted", "1").add("updated_at", updatedAt);
        Result result = commentDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        BlogCommentPO po = (BlogCommentPO) getResult.getData();
        WebContext.setAttributeForRequest(BlogConstants.REQUEST_DATA, po);
        return ResultUtils.success(params.getId());
    }

    // -------------------- 辅助方法 --------------------------

    /**
     * 封装给定的 blogPO 的信息, 封装 floorId, commentId 等等
     *
     * @param po     po
     * @param params params
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/24/2017 6:10 PM
     * @since 1.0
     */
    private Result encapCommentPO(BlogPO blog, BlogCommentPO po, CommentSaveForm params) {
        String replyExtracted = BizUtils.extractReplyFrom(params.getComment(), constantsContext.replyCommentPrefix, constantsContext.replyCommentSuffix);
        boolean isReply = ((!Tools.isEmpty(params.getFloorId())) && (replyExtracted != null));
        if (isReply) {
            IQueryCriteria query = Criteria.and(Criteria.eq("deleted", "0")).add(Criteria.eq("blog_id", params.getBlogId()))
                    .add(Criteria.eq("floor_id", params.getFloorId())).add(Criteria.eq("comment_id", params.getCommentId()));
            Result getCommentResult = commentDao.get(query);
            if (!getCommentResult.isSuccess()) {
                return getCommentResult;
            }
            BlogCommentPO replyTo = (BlogCommentPO) getCommentResult.getData();
            if (!params.getToUser().equalsIgnoreCase(replyTo.getName())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 该楼层没有对应的回复 ! ");
            }

            po.setFloorId(params.getFloorId());
            po.setComment(replyExtracted);
            po.setParentCommentId(replyTo.getId());
        } else {
            po.setFloorId(cacheContext.nextFloorId(po.getBlogId()));
            po.setToUser(blog.getAuthor());
        }
        po.setCommentId(cacheContext.nextCommentId(po.getBlogId(), po.getFloorId()));

        return ResultUtils.success(po);
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
            condSqlSb.append(" and b.blog_id = ? ");
            selectParams.add(params.getBlogId());
        }
        if (!Tools.isEmpty(params.getBlogTypeId())) {
            condSqlSb.append(" and b.blog_type_id = ? ");
            selectParams.add(params.getBlogTypeId());
        }
        if (!Tools.isEmpty(params.getBlogName())) {
            condSqlSb.append(" and b.title like ? ");
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

    /**
     * 封装 查询 某个博客, 某一层的所有的评论
     *
     * @param params       params
     * @param condSql      condSql
     * @param selectParams selectParams
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 5:59 PM
     * @since 1.0
     */
    private void encapFloorCommentList(FloorCommentListSearchForm params, StringBuilder condSql, List<Object> selectParams) {
        if (!Tools.isEmpty(params.getBlogId())) {
            condSql.append(" and c.blog_id = ? ");
            selectParams.add(params.getBlogId());
        }
        if (!Tools.isEmpty(params.getFloorId())) {
            condSql.append(" and c.floor_id = ? ");
            selectParams.add(params.getFloorId());
        }
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
    public List<List<CommentVO>> generateCommentTree(List<CommentVO> comments) {
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
