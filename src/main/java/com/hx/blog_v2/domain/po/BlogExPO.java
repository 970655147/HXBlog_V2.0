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
    @JSONField({"goodCnt", "good_cnt"})
    private int goodCnt;
    @JSONField({"notGoodCnt", "not_good_cnt"})
    private int notGoodCnt;

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

    public int getGoodCnt() {
        return goodCnt;
    }

    public void setGoodCnt(int goodCnt) {
        this.goodCnt = goodCnt;
    }

    public int getNotGoodCnt() {
        return notGoodCnt;
    }

    public void setNotGoodCnt(int notGoodCnt) {
        this.notGoodCnt = notGoodCnt;
    }

    public void incCommentCnt(int inc) {
        commentCnt += inc;
    }

    public void incViewCnt(int inc) {
        viewCnt += inc;
    }

    public void incGoodCnt(int inc) {
        goodCnt += inc;
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
