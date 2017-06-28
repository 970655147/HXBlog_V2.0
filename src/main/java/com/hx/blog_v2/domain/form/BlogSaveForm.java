package com.hx.blog_v2.domain.form;

/**
 * 博客的一条记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class BlogSaveForm {

    private String id;
    private String title;
    private String coverUrl;
    private String blogCreateTypeId;
    private String blogTypeId;
    private String blogTagIds;
    private String state;
    private String summary;
    private String content;

    public BlogSaveForm() {
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

    public String getBlogTagIds() {
        return blogTagIds;
    }

    public void setBlogTagIds(String blogTagIds) {
        this.blogTagIds = blogTagIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
