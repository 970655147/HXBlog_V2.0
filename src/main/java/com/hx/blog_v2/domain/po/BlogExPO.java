package com.hx.blog_v2.domain.po;

import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * 博客的额外信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:46 AM
 */
public class BlogExPO implements JSONTransferable<BlogExPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"blogId", "blog_id"})
    private String blogId;
    @JSONField({"commentCnt", "comment_cnt"})
    private int commentCnt;
    @JSONField({"viewCnt", "view_cnt"})
    private int viewCnt;
    @JSONField({"dayFlushViewCnt", "day_flush_view_cnt"})
    private int dayFlushViewCnt;
    @JSONField({"uniqueViewCnt", "unique_view_cnt"})
    private int uniqueViewCnt;
    @JSONField({"good1Cnt", "good1_cnt"})
    private int good1Cnt;
    @JSONField({"good2Cnt", "good2_cnt"})
    private int good2Cnt;
    @JSONField({"good3Cnt", "good3_cnt"})
    private int good3Cnt;
    @JSONField({"good4Cnt", "good4_cnt"})
    private int good4Cnt;
    @JSONField({"good5Cnt", "good5_cnt"})
    private int good5Cnt;
    @JSONField({"goodTotalCnt", "good_total_cnt"})
    private int goodTotalCnt;
    @JSONField({"goodTotalScore", "good_total_score"})
    private int goodTotalScore;

    public BlogExPO(String blogId) {
        this.blogId = blogId;
    }

    public BlogExPO() {
    }

    /**
     * setter & getter
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public int getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(int viewCnt) {
        this.viewCnt = viewCnt;
    }

    public int getDayFlushViewCnt() {
        return dayFlushViewCnt;
    }

    public void setDayFlushViewCnt(int dayFlushViewCnt) {
        this.dayFlushViewCnt = dayFlushViewCnt;
    }

    public int getUniqueViewCnt() {
        return uniqueViewCnt;
    }

    public void setUniqueViewCnt(int uniqueViewCnt) {
        this.uniqueViewCnt = uniqueViewCnt;
    }

    public int getGood1Cnt() {
        return good1Cnt;
    }

    public void setGood1Cnt(int good1Cnt) {
        this.good1Cnt = good1Cnt;
    }

    public int getGood2Cnt() {
        return good2Cnt;
    }

    public void setGood2Cnt(int good2Cnt) {
        this.good2Cnt = good2Cnt;
    }

    public int getGood3Cnt() {
        return good3Cnt;
    }

    public void setGood3Cnt(int good3Cnt) {
        this.good3Cnt = good3Cnt;
    }

    public int getGood4Cnt() {
        return good4Cnt;
    }

    public void setGood4Cnt(int good4Cnt) {
        this.good4Cnt = good4Cnt;
    }

    public int getGood5Cnt() {
        return good5Cnt;
    }

    public void setGood5Cnt(int good5Cnt) {
        this.good5Cnt = good5Cnt;
    }

    public int getGoodTotalCnt() {
        return goodTotalCnt;
    }

    public void setGoodTotalCnt(int goodTotalCnt) {
        this.goodTotalCnt = goodTotalCnt;
    }

    public int getGoodTotalScore() {
        return goodTotalScore;
    }

    public void setGoodTotalScore(int goodTotalScore) {
        this.goodTotalScore = goodTotalScore;
    }

    public void incCommentCnt(int inc) {
        commentCnt += inc;
    }

    public void incViewCnt(int inc) {
        viewCnt += inc;
    }

    public void incDayFlushViewCnt(int inc) {
        dayFlushViewCnt += inc;
    }

    public void incUniqueViewCnt(int inc) {
        uniqueViewCnt += inc;
    }

    public void incGood1Cnt(int inc) {
        good1Cnt += inc;
    }

    public void incGood2Cnt(int inc) {
        good2Cnt += inc;
    }

    public void incGood3Cnt(int inc) {
        good3Cnt += inc;
    }

    public void incGood4Cnt(int inc) {
        good4Cnt += inc;
    }

    public void incGood5Cnt(int inc) {
        good5Cnt += inc;
    }

    /**
     * 处理 当前 blog 增加一个 score 的处理
     *
     * @param score score
     * @param inc   inc
     * @return void
     * @author Jerry.X.He
     * @date 6/23/2017 9:40 PM
     * @since 1.0
     */
    public void incGoodCnt(int score, int inc) {
        if (score == 1) {
            incGood1Cnt(inc);
        } else if (score == 2) {
            incGood2Cnt(inc);
        } else if (score == 3) {
            incGood3Cnt(inc);
        } else if (score == 4) {
            incGood4Cnt(inc);
        } else if (score == 5) {
            incGood5Cnt(inc);
        } else if(score == 0) {
            // ignore
        } else {
            Tools.assert0(" unknown sense score ! ");
        }

        goodTotalCnt += 1;
        goodTotalScore += score;
    }

    public void decGoodCnt(int score, int inc) {
        if (score == 1) {
            incGood1Cnt(inc);
        } else if (score == 2) {
            incGood2Cnt(inc);
        } else if (score == 3) {
            incGood3Cnt(inc);
        } else if (score == 4) {
            incGood4Cnt(inc);
        } else if (score == 5) {
            incGood5Cnt(inc);
        } else if(score == 0) {
            // ignore, offset the goodTotalCnt
            // 0 -> [1|2|3|4|5]
            goodTotalCnt += 1;
        } else {
            Tools.assert0(" unknown sense score ! ");
        }

        goodTotalCnt -= 1;
        goodTotalScore -= score;
    }

    public void incGoodTotalCnt(int inc) {
        goodTotalCnt += inc;
    }

    public void incGoodTotalScore(int inc) {
        goodTotalScore += inc;
    }

    public static final BlogExPO PROTO_BEAN = new BlogExPO();

    @Override
    public BlogExPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(BlogExPO.class, this, config);
        return this;
    }

    @Override
    public JSONObject encapJSON(JSONConfig config) {
        return encapJSON(config, new LinkedList<>());
    }

    @Override
    public JSONObject encapJSON(JSONConfig config, Deque<Object> cycleDectector) {
        if (cycleDectector.contains(this)) {
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()));
        }
        cycleDectector.push(this);

        JSONObject result = JSONObject.fromObject(this, config);
        return result;
    }

    @Override
    public BlogExPO newInstance(Object... args) {
        return new BlogExPO();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void id(String id) {
        this.id = id;
    }

}
