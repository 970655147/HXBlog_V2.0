package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.BlogCommentDao;
import com.hx.blog_v2.domain.form.*;
import com.hx.blog_v2.domain.mapper.AdminCommentVOMapper;
import com.hx.blog_v2.domain.mapper.CommentVOMapper;
import com.hx.blog_v2.domain.po.BlogCommentPO;
import com.hx.blog_v2.domain.vo.AdminCommentVO;
import com.hx.blog_v2.domain.vo.CommentVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.BlogCommentService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.CacheContext;
import com.hx.blog_v2.util.DateUtils;
import com.hx.blog_v2.util.SqlUtils;
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
        SessionUser user = getSessionUser();
        BlogCommentPO po = new BlogCommentPO(user.getUserName(), user.getEmail(), user.getHeadImgUrl(), params.getToUser(),
                user.getRole(), params.getContent());
        po.setBlogId(params.getBlogId());
        if(! Tools.isEmpty(params.getFloorId())) {
            po.setFloorId(params.getFloorId());
        } else {
            po.setFloorId(cacheContext.nextFloorId(po.getBlogId()));
        }
        po.setCommentId(cacheContext.nextCommentId(po.getBlogId(), po.getFloorId()));
        po.setParentCommentId(params.getId() == null ? BlogConstants.RESOURCE_ROOT_PARENT_ID : params.getId());

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
        StringBuilder sql = new StringBuilder(" select c.*, b.title as blog_name from blog_comment as c inner join blog as b on c.blog_id = b.id " +
                " where c.deleted = 0 ");
        List<Object> sqlParams = new ArrayList<>(10);
        if (!Tools.isEmpty(params.getBlogId())) {
            sql.append(" and b.blog_id = ");
            sqlParams.add(params.getBlogId());
        }
        if (!Tools.isEmpty(params.getBlogTypeId())) {
            sql.append(" and b.blog_type_id = ");
            sqlParams.add(params.getBlogTypeId());
        }
        if (!Tools.isEmpty(params.getBlogName())) {
            sql.append(" and b.name like ? ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getBlogTypeId()));
        }
        if (!Tools.isEmpty(params.getUserName())) {
            sql.append(" and c.name like ? ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getUserName()));
        }
        if (!Tools.isEmpty(params.getToUserName())) {
            sql.append(" and c.to_user like ? ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getToUserName()));
        }
        if (!Tools.isEmpty(params.getContent())) {
            sql.append(" and c.content like ? ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getContent()));
        }
        // 如果没有上述三个模糊匹配, 则查询keywords
        if (!Tools.isEmpty(params.getKeywords()) && (sqlParams.size() == 0)) {
            sql.append(" (c.name like ? or c.to_user like ? or c.content like ?) ");
            sqlParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
            sqlParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
            sqlParams.add(SqlUtils.wrapWildcard(params.getKeywords()));
        }

        // blogTagId 查询

        sql.append(String.format(" order by %s %s ", AdminCommentSearchForm.SORT_2_FIELD.get(params.getSort()),
                params.isAsc() ? " asc " : " desc "));
        sql.append(" limit ?, ? ");
        sqlParams.add(page.recordOffset());
        sqlParams.add(page.getPageSize());

        List<AdminCommentVO> list = jdbcTemplate.query(sql.toString(), sqlParams.toArray(), new AdminCommentVOMapper());
        page.setList(list);
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
            sqlParams.add(params.getBlogId());
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
                    Criteria.set("to_user", params.getToUser()).add("content", params.getContent())
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

    private SessionUser getSessionUser() {
//        HttpSession session = WebContext.getSession();
//        SessionUser user = (SessionUser) session.getAttribute(BlogConstants.SESSION_USER);
        return new SessionUser("Jerry.X.He", "970655147@qq.com", "https://gss0.bdstatic.com/6LZ1dD3d1sgCo2Kml5_Y_D3/sys/portrait/item/48c0c0b6b7e7393730363535313437f02d", "admin");
    }

}
