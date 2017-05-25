package com.hx.blog_v2.domain.form;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;

import java.util.*;

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
    private String author;
    /**
     * 封面的url
     */
    private String coverUrl;
    /**
     * 所属的分类
     */
    private String blogTypeId;
    /**
     * 博客的标签列表
     */
    private String blogTagIds;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 博客内容
     */
    private String content;

    private String createdAt;
    private String createdAtMonth;
    private String updatedAt;

    public BlogSaveForm(String title, String author, String coverUrl, String blogTypeId, String blogTagIds,
                        String summary, String content) {
        this();
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.blogTypeId = blogTypeId;
        this.blogTagIds = blogTagIds;
        this.summary = summary;
        this.content = content;
    }

    public BlogSaveForm() {
        Date now = new Date();
        createdAtMonth = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM);
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
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

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getCreatedAtMonth() {
        return createdAtMonth;
    }

    public void setCreatedAtMonth(String createdAtMonth) {
        this.createdAtMonth = createdAtMonth;
    }
}
