package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.dto.CorrectionType;
import com.hx.blog_v2.domain.dto.StringStringPair;
import com.hx.blog_v2.domain.form.CorrectionSearchForm;
import com.hx.blog_v2.domain.form.DoCorrectionForm;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.blog_v2.domain.mapper.StringStringPairMapper;
import com.hx.blog_v2.domain.vo.CorrectionVO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.CorrectionService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.blog_v2.util.SqlUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONArray;
import com.hx.log.util.Tools;
import com.hx.mongo.criteria.Criteria;
import com.hx.mongo.criteria.interf.IQueryCriteria;
import com.hx.mongo.criteria.interf.IUpdateCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * CorrectionServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:27 PM
 */
@Service
public class CorrectionServiceImpl extends BaseServiceImpl<Object> implements CorrectionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BlogExDao blogExDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result list(CorrectionSearchForm params) {
        CorrectionType type = CorrectionType.of(params.getType());
        if (CorrectionType.COMMENT_CNT == type) {
            return collectCommentCorrection();
        } else {
            return ResultUtils.failed(" 没有这个类型 ! ");
        }
    }

    @Override
    public Result doCorrection(DoCorrectionForm params) {
        CorrectionType type = CorrectionType.of(params.getType());
        if (CorrectionType.COMMENT_CNT == type) {
            return doCorrectionComment(params);
        } else {
            return ResultUtils.failed(" 没有这个类型 ! ");
        }
    }

    // ----------------- 辅助方法 -----------------------

    /**
     * 收集需要校正的 博客 -> 评论数量
     *
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/18/2017 7:35 PM
     * @since 1.0
     */
    private Result collectCommentCorrection() {
        List<CorrectionVO> vos = collectCommentCorrection0();
        if (Tools.isEmpty(vos)) {
            return ResultUtils.success(Collections.emptyList());
        }

        /**
         * 封装附加信息
         */
        String getBlogInfoSqlTemplate = " select id, title from blog where id in ( %s ) ";
        String getBlogInfoSql = String.format(getBlogInfoSqlTemplate, SqlUtils.wrapInSnippet(vos));
        List<StringStringPair> blogId2Title = jdbcTemplate.query(getBlogInfoSql, new StringStringPairMapper("id", "title"));
        for (CorrectionVO vo : vos) {
            StringStringPair pair = BizUtils.findByLogisticId(blogId2Title, vo.getId());
            if (pair == null) {
                continue;
            }

            vo.setContextInfo(" blog : " + pair.getRight());
            vo.setDesc(" 该博客评论数量偏差 : " + (Integer.parseInt(vo.getExpect()) - Integer.parseInt(vo.getValue())));
        }

        return ResultUtils.success(vos);
    }

    /**
     * 处理校正评论的相关业务
     *
     * @param params params
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/18/2017 9:20 PM
     * @since 1.0
     */
    private Result doCorrectionComment(DoCorrectionForm params) {
        if (!Tools.isEmpty(params.getId())) {
            StringStringPair pair = collectCommentCntPair(params.getId());
            return doCorrection0(params.getId(), pair.getLeft());
        } else {
            JSONArray errorIds = new JSONArray();
            List<CorrectionVO> needToCorrection = collectCommentCorrection0();
            for (CorrectionVO vo : needToCorrection) {
                Result result = doCorrection0(vo.getId(), vo.getExpect());
                if (!result.isSuccess()) {
                    errorIds.add(vo.getId());
                }
            }
            String statsInfo = " 总共 : " + needToCorrection.size() + ", 失败 : " + errorIds.size() + ", 失败记录 : " + String.valueOf(errorIds);
            return ResultUtils.success(statsInfo);
        }
    }


    /**
     * 获取需要校正评论的 校正记录
     *
     * @return java.util.List<com.hx.blog_v2.domain.vo.CorrectionVO>
     * @author Jerry.X.He
     * @date 6/18/2017 8:45 PM
     * @since 1.0
     */
    private List<CorrectionVO> collectCommentCorrection0() {
        String getBlogCommentSql = " select blog_id, count(*) as comment_cnt from blog_comment where deleted = 0 and comment_id = 1 group by blog_id ";
        List<StringStringPair> blog2CommentCnt = jdbcTemplate.query(getBlogCommentSql, new StringStringPairMapper("blog_id", "comment_cnt"));
        if(Tools.isEmpty(blog2CommentCnt)) {
            return Collections.emptyList();
        }

        String getExCommentSqlTemplate = "select blog_id, comment_cnt from blog_ex where blog_id in ( %s ) ";
        String getExCommentSql = String.format(getExCommentSqlTemplate, SqlUtils.wrapInSnippet(blog2CommentCnt));
        List<StringStringPair> blog2CommentEx = jdbcTemplate.query(getExCommentSql, new StringStringPairMapper("blog_id", "comment_cnt"));

        List<CorrectionVO> vos = new ArrayList<>(blog2CommentEx.size());
        for (StringStringPair pair : blog2CommentCnt) {
            StringStringPair pairEx = BizUtils.findByLogisticId(blog2CommentEx, pair.logisticalId());
            if (pairEx == null) {
                continue;
            }

            if (!Objects.equals(pair.getRight(), pairEx.getRight())) {
                CorrectionVO vo = new CorrectionVO(pair.getLeft(), pair.getRight(), pairEx.getRight());
                vos.add(vo);
            }
        }

        return vos;
    }

    /**
     * 获取给定的博客的 评论数量
     *
     * @return java.util.List<com.hx.blog_v2.domain.vo.CorrectionVO>
     * @author Jerry.X.He
     * @date 6/18/2017 8:45 PM
     * @since 1.0
     */
    private StringStringPair collectCommentCntPair(String blogId) {
        Object[] params = new Object[]{blogId};
        String getBlogCommentSql = " select IFNULL(count(*), 0) as comment_cnt from blog_comment where deleted = 0 and blog_id =? and comment_id = 1 group by blog_id ";
        String commentCnt = jdbcTemplate.queryForObject(getBlogCommentSql, params, new OneStringMapper("comment_cnt"));
        String getExCommentSql = "select comment_cnt from blog_ex where blog_id = ? ";
        String commentCntEx = jdbcTemplate.queryForObject(getExCommentSql, params, new OneStringMapper("comment_cnt"));
        return new StringStringPair(commentCnt, commentCntEx);
    }

    /**
     * 更新给定的博客的 commentCnt
     *
     * @param blogId     blogId
     * @param commentCnt commentCnt
     * @return void
     * @author Jerry.X.He
     * @date 6/18/2017 8:53 PM
     * @since 1.0
     */
    private Result doCorrection0(String blogId, String commentCnt) {
        IQueryCriteria query = Criteria.eq("blog_id", blogId);
        IUpdateCriteria update = Criteria.set("comment_cnt", commentCnt);

        Result result = blogExDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }

        cacheContext.removeBlogEx(blogId);
        return ResultUtils.success(" success ");
    }

}
