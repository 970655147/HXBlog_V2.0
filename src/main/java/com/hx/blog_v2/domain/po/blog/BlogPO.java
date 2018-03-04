package com.hx.blog_v2.domain.po.blog;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * 博客的一条记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class BlogPO implements JSONTransferable<BlogPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"title", "title"})
    private String title;
    @JSONField({"author", "author"})
    private String author;
    @JSONField({"coverUrl", "cover_url"})
    private String coverUrl;
    @JSONField({"blogCreateTypeId", "blog_create_type_id"})
    private String blogCreateTypeId;
    @JSONField({"blogTypeId", "blog_type_id"})
    private String blogTypeId;
    @JSONField({"state", "state"})
    private String state;
    @JSONField({"summary", "summary"})
    private String summary;
    @JSONField({"contentUrl", "content_url"})
    private String contentUrl;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"createdAtMonth", "created_at_month"})
    private String createdAtMonth;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"deleted", "deleted"})
    private int deleted;


    public BlogPO(String title, String author, String coverUrl, String blogCreateTypeId,
                  String blogTypeId, String state, String summary, String contentUrl) {
        this();
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.blogCreateTypeId = blogCreateTypeId;
        this.blogTypeId = blogTypeId;
        this.state = state;
        this.summary = summary;
        this.contentUrl = contentUrl;
    }

    public BlogPO() {
        Date now = new Date();
        createdAtMonth = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM);
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        deleted = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getBlogCreateTypeId() {
        return blogCreateTypeId;
    }

    public void setBlogCreateTypeId(String blogCreateTypeId) {
        this.blogCreateTypeId = blogCreateTypeId;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAtMonth() {
        return createdAtMonth;
    }

    public void setCreatedAtMonth(String createdAtMonth) {
        this.createdAtMonth = createdAtMonth;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public static final BlogPO PROTO_BEAN = new BlogPO();

    @Override
    public BlogPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(BlogPO.class, this, config);
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
    public BlogPO newInstance(Object... args) {
        return new BlogPO();
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
