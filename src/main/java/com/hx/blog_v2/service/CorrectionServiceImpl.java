package com.hx.blog_v2.service;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.BlogExDao;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.CorrectionType;
import com.hx.blog_v2.domain.dto.SenseType;
import com.hx.blog_v2.domain.dto.StringIntPair;
import com.hx.blog_v2.domain.dto.StringStringPair;
import com.hx.blog_v2.domain.form.CorrectionSearchForm;
import com.hx.blog_v2.domain.form.DoCorrectionForm;
import com.hx.blog_v2.domain.mapper.CommonPOMapper;
import com.hx.blog_v2.domain.mapper.OneStringMapper;
import com.hx.blog_v2.domain.mapper.StringIntPairMapper;
import com.hx.blog_v2.domain.mapper.StringStringPairMapper;
import com.hx.blog_v2.domain.po.BlogExPO;
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

import java.util.*;

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
        CorrectionType type = CorrectionType.of(params.getCode());
        if (CorrectionType.COMMENT_CNT == type) {
            return collectCommentCorrection();
        } else if (CorrectionType.SCORE_CNT == type) {
            return collectScoreCorrection();
        } else {
            return ResultUtils.failed(" 没有这个类型 ! ");
        }
    }

    @Override
    public Result doCorrection(DoCorrectionForm params) {
        CorrectionType type = CorrectionType.of(params.getCode());
        if (CorrectionType.COMMENT_CNT == type) {
            return doCorrectionComment(params);
        } else if (CorrectionType.SCORE_CNT == type) {
            return doCorrectionScore(params);
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
        // 刷新评论数据 到磁盘
        cacheContext.clearBlogExCached();
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
     * 收集需要校正的 评分的博客的信息
     *
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/18/2017 7:35 PM
     * @since 1.0
     */
    private Result collectScoreCorrection() {
        // 将缓存的数据, 持久化到 db
        cacheContext.clearScoreCached();

        String scoreByBlogIdSql = " select blog_id, score from blog_sense where sense = ? group by blog_id, score ";
        List<StringIntPair> scoreByBlogId = jdbcTemplate.query(scoreByBlogIdSql, new Object[]{SenseType.GOOD.code()}, new StringIntPairMapper("blog_id", "score"));
        Set<String> blogIds = new HashSet<>();
        Map<String, Integer> blogScore2Cnt = new HashMap<>();
        for (StringIntPair pair : scoreByBlogId) {
            blogIds.add(pair.getLeft());

            String blogScoreKey = blogId2ScoreKey(pair.getLeft(), pair.getRight());
            Integer cnt = blogScore2Cnt.get(blogScoreKey);
            if (cnt == null) {
                cnt = 0;
            }
            blogScore2Cnt.put(blogScoreKey, cnt + 1);
        }

        List<CorrectionVO> vos = collectScoreCorrectionVo(blogIds, blogScore2Cnt);
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
            cacheContext.removeBlogEx(params.getId());
            return doCorrectionComment0(params.getId(), pair.getLeft());
        } else {
            cacheContext.clearBlogExCached();
            JSONArray errorIds = new JSONArray();
            List<CorrectionVO> needToCorrection = collectCommentCorrection0();
            for (CorrectionVO vo : needToCorrection) {
                Result result = doCorrectionComment0(vo.getId(), vo.getExpect());
                if (!result.isSuccess()) {
                    errorIds.add(vo.getId());
                }
            }
            String statsInfo = " 总共 : " + needToCorrection.size() + ", 失败 : " + errorIds.size() + ", 失败记录 : " + String.valueOf(errorIds);
            return ResultUtils.success(statsInfo);
        }
    }

    /**
     * 校验博客的评分
     *
     * @param params params
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/24/2017 11:06 AM
     * @since 1.0
     */
    private Result doCorrectionScore(DoCorrectionForm params) {
        if (!Tools.isEmpty(params.getId())) {
            return doCorrectionScore0(params.getId());
        } else {
            return doCorrectionAllScore0();
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
        if (Tools.isEmpty(blog2CommentCnt)) {
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
    private Result doCorrectionComment0(String blogId, String commentCnt) {
        IQueryCriteria query = Criteria.eq("blog_id", blogId);
        IUpdateCriteria update = Criteria.set("comment_cnt", commentCnt);

        Result result = blogExDao.update(query, update);
        if (!result.isSuccess()) {
            return result;
        }
        return ResultUtils.success(" success ");
    }

    /**
     * 校正给定的博客的评分
     *
     * @param blogId blogId
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/24/2017 11:08 AM
     * @since 1.0
     */
    private Result doCorrectionScore0(String blogId) {
        // 将缓存的数据, 持久化到 db
        cacheContext.clearScoreCached();

        String scoreByBlogIdSql = " select score from blog_sense where sense = ? and blog_id = ? group by blog_id, score ";
        List<String> scores = jdbcTemplate.query(scoreByBlogIdSql, new Object[]{SenseType.GOOD.code(), blogId}, new OneStringMapper("score"));
        String blogExSql = " select blog_id, good1_cnt, good2_cnt, good3_cnt, good4_cnt, good5_cnt, good_total_cnt, good_total_score from blog_ex where blog_id = ? ";
        List<BlogExPO> blogExes = jdbcTemplate.query(blogExSql, new Object[]{blogId}, new CommonPOMapper<>(BlogExPO.PROTO_BEAN));
        if (Tools.isEmpty(blogExes) || (blogExes.size() > 1)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的博客不存在 或者有问题 ! ");
        }

        Map<String, Integer> cntByScore = new HashMap<>();
        for (String score : scores) {
            Integer cnt = cntByScore.get(score);
            if (cnt == null) {
                cnt = 0;
            }
            cntByScore.put(String.valueOf(score), cnt + 1);
        }
        BlogExPO ex = blogExes.get(0);
        return doCorrectionScore0(cntByScore, ex);
    }

    private Result doCorrectionAllScore0() {
        // 将缓存的数据, 持久化到 db
        cacheContext.clearScoreCached();

        String scoreByBlogIdSql = " select blog_id, score from blog_sense where sense = ? group by blog_id, score ";
        List<StringIntPair> scoreByBlogId = jdbcTemplate.query(scoreByBlogIdSql, new Object[]{SenseType.GOOD.code()}, new StringIntPairMapper("blog_id", "score"));
        Set<String> blogIds = new HashSet<>();
        Map<String, Integer> blogScore2Cnt = new HashMap<>();
        for (StringIntPair pair : scoreByBlogId) {
            blogIds.add(pair.getLeft());
            String blogScoreKey = blogId2ScoreKey(pair.getLeft(), pair.getRight());
            Integer cnt = blogScore2Cnt.get(blogScoreKey);
            if (cnt == null) {
                cnt = 0;
            }
            blogScore2Cnt.put(blogScoreKey, cnt + 1);
        }
        if (Tools.isEmpty(blogIds)) {
            String statsInfo = " 总共 : " + 0 + ", 失败 : " + 0 + ", 失败记录 : " + 0;
            return ResultUtils.success(statsInfo);
        }

        return doCorrectionAllScore0(blogIds, blogScore2Cnt);
    }

    /**
     * 计算 blogEx 和 blogSense 中的数据 是否匹配, 返回需要校验的数据
     *
     * @param blogIds       blogIds
     * @param blogScore2Cnt blogScore2Cnt
     * @return java.util.List<com.hx.blog_v2.domain.vo.CorrectionVO>
     * @author Jerry.X.He
     * @date 6/24/2017 10:58 AM
     * @since 1.0
     */
    private List<CorrectionVO> collectScoreCorrectionVo(Set<String> blogIds, Map<String, Integer> blogScore2Cnt) {
        if (Tools.isEmpty(blogIds)) {
            return Collections.emptyList();
        }

        String blogExSqlTemplate = " select blog_id, good1_cnt, good2_cnt, good3_cnt, good4_cnt, good5_cnt, good_total_cnt, good_total_score from blog_ex where blog_id in ( %s ) ";
        String blogExSql = String.format(blogExSqlTemplate, SqlUtils.wrapInSnippetForIds(blogIds));
        List<BlogExPO> blogExes = jdbcTemplate.query(blogExSql, new CommonPOMapper<>(BlogExPO.PROTO_BEAN));
        String getBlogInfoSqlTemplate = " select id, title from blog where id in ( %s ) ";
        String getBlogInfoSql = String.format(getBlogInfoSqlTemplate, SqlUtils.wrapInSnippetForIds(blogIds));
        List<StringStringPair> blogId2Title = jdbcTemplate.query(getBlogInfoSql, new StringStringPairMapper("id", "title"));

        List<CorrectionVO> result = new ArrayList<>(blogExes.size());
        for (BlogExPO ex : blogExes) {
            boolean needValidate = false;
            int goodTotalCnt = 0, goodTotalScore = 0;
            JSONArray desc = new JSONArray();
            for (int i = 1; i <= 5; i++) {
                String blogScoreKey = blogId2ScoreKey(ex.getBlogId(), i);
                int goodCnt = Tools.optInt((Map) blogScore2Cnt, blogScoreKey, 0);
                goodTotalCnt += goodCnt;
                goodTotalScore += i * goodCnt;
                if (goodCnt != ex.getGoodCnt(i)) {
                    desc.add("good" + i);
                    needValidate = true;
                }
            }
            if (goodTotalCnt != ex.getGoodTotalCnt()) {
                desc.add("goodTotalCnt");
                needValidate = true;
            }
            if (goodTotalScore != ex.getGoodTotalScore()) {
                desc.add("goodTotalScore");
                needValidate = true;
            }

            if (needValidate) {
                JSONArray expect = new JSONArray(), value = new JSONArray();
                for (int i = 1; i <= 5; i++) {
                    String blogScoreKey = blogId2ScoreKey(ex.getBlogId(), i);
                    int goodCnt = Tools.optInt((Map) blogScore2Cnt, blogScoreKey, 0);
                    expect.add(goodCnt);
                    value.add(ex.getGoodCnt(i));
                }
                expect.add(goodTotalCnt);
                expect.add(goodTotalScore);
                value.add(ex.getGoodTotalCnt());
                value.add(ex.getGoodTotalScore());

                StringStringPair pair = BizUtils.findByLogisticId(blogId2Title, ex.getBlogId());
                CorrectionVO vo = new CorrectionVO(ex.getBlogId(), expect.toString(), value.toString());
                vo.setContextInfo(" blog : " + pair.getRight());
                vo.setDesc(" 不匹配 : " + desc.toString());
                result.add(vo);
            }
        }

        return result;
    }

    /**
     * 处理校验的业务, 以及 更新数据库的数据
     *
     * @param cntByScore cntByScore
     * @param ex         ex
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/24/2017 11:27 AM
     * @since 1.0
     */
    private Result doCorrectionScore0(Map<String, Integer> cntByScore, BlogExPO ex) {
        Map<String, Integer> updateMap = new HashMap<>();
        int goodTotalCnt = 0, goodTotalScore = 0;
        for (int i = 1; i <= 5; i++) {
            int goodCnt = Tools.optInt((Map) cntByScore, String.valueOf(i), 0);
            goodTotalCnt += goodCnt;
            goodTotalScore += i * goodCnt;
            if (goodCnt != ex.getGoodCnt(i)) {
                updateMap.put("good" + i + "_cnt", goodCnt);
            }
        }
        if (goodTotalCnt != ex.getGoodTotalCnt()) {
            updateMap.put("good_total_cnt", goodTotalCnt);
        }
        if (goodTotalScore != ex.getGoodTotalScore()) {
            updateMap.put("good_total_score", goodTotalScore);
        }
        if (Tools.isEmpty(updateMap)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 没有需要更新的字段 ! ");
        }

        IQueryCriteria query = Criteria.eq("blog_id", ex.getBlogId());
        IUpdateCriteria update = Criteria.set((Map) updateMap);
        Result updateResult = blogExDao.update(query, update, true);
        if (!updateResult.isSuccess()) {
            return updateResult;
        }
        return ResultUtils.success(" success ");
    }

    /**
     * 查询 真实的 blogEx, 并比较校正不准确的记录
     *
     * @param blogIds       blogIds
     * @param blogScore2Cnt blogScore2Cnt
     * @return com.hx.common.interf.common.Result
     * @author Jerry.X.He
     * @date 6/24/2017 11:46 AM
     * @since 1.0
     */
    private Result doCorrectionAllScore0(Set<String> blogIds, Map<String, Integer> blogScore2Cnt) {
        String blogExSqlTemplate = " select blog_id, good1_cnt, good2_cnt, good3_cnt, good4_cnt, good5_cnt, good_total_cnt, good_total_score from blog_ex where blog_id in ( %s ) ";
        String blogExSql = String.format(blogExSqlTemplate, SqlUtils.wrapInSnippetForIds(blogIds));
        List<BlogExPO> blogExes = jdbcTemplate.query(blogExSql, new CommonPOMapper<>(BlogExPO.PROTO_BEAN));

        JSONArray errorIds = new JSONArray();
        for (BlogExPO ex : blogExes) {
            Map<String, Integer> updateMap = new HashMap<>();
            int goodTotalCnt = 0, goodTotalScore = 0;
            for (int i = 1; i <= 5; i++) {
                String blogScoreKey = blogId2ScoreKey(ex.getBlogId(), i);
                int goodCnt = Tools.optInt((Map) blogScore2Cnt, blogScoreKey, 0);
                goodTotalCnt += goodCnt;
                goodTotalScore += i * goodCnt;
                if (goodCnt != ex.getGoodCnt(i)) {
                    updateMap.put("good" + i + "_cnt", goodCnt);
                }
            }
            if (goodTotalCnt != ex.getGoodTotalCnt()) {
                updateMap.put("good_total_cnt", goodTotalCnt);
            }
            if (goodTotalScore != ex.getGoodTotalScore()) {
                updateMap.put("good_total_score", goodTotalScore);
            }

            if (!Tools.isEmpty(updateMap)) {
                IQueryCriteria query = Criteria.eq("blog_id", ex.getBlogId());
                IUpdateCriteria update = Criteria.set((Map) updateMap);
                Result updateResult = blogExDao.update(query, update, true);
                if (!updateResult.isSuccess()) {
                    errorIds.add(ex.getBlogId());
                }
            }
        }

        String statsInfo = " 总共 : " + blogExes.size() + ", 失败 : " + errorIds.size() + ", 失败记录 : " + errorIds.toString();
        return ResultUtils.success(statsInfo);
    }

    /**
     * 得到一个 blogId, score 标志的的key
     *
     * @param blogId blogId
     * @param score  score
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 6/24/2017 10:33 AM
     * @since 1.0
     */
    private String blogId2ScoreKey(String blogId, int score) {
        return blogId + "_" + score;
    }

}
