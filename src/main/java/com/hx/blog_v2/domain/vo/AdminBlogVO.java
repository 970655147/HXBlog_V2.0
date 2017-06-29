package com.hx.blog_v2.domain.vo;

import java.util.List;

/**
 * 博客的一条记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class AdminBlogVO {

    private String id;
    private String title;
    private String author;
    /**
     * 封面的url
     */
    private String coverUrl;
    /**
     * 创建类型
     */
    private String blogCreateTypeId;
    private String blogCreateTypeName;
    private String blogCreateTypeImgUrl;
    /**
     * 所属的分类
     */
    private String blogTypeId;
    private String blogTypeName;
    /**
     * tag 列表
     */
    private List<String> blogTagIds;
    private List<String> blogTagNames;
    private String state;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 内容的映射
     */
    private String contentUrl;
    private String content;

    private String createdAt;
    private String createdAtMonth;
    private String updatedAt;

    public AdminBlogVO() {

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

    public String getBlogCreateTypeName() {
        return blogCreateTypeName;
    }

    public void setBlogCreateTypeName(String blogCreateTypeName) {
        this.blogCreateTypeName = blogCreateTypeName;
    }

    public String getBlogCreateTypeImgUrl() {
        return blogCreateTypeImgUrl;
    }

    public void setBlogCreateTypeImgUrl(String blogCreateTypeImgUrl) {
        this.blogCreateTypeImgUrl = blogCreateTypeImgUrl;
    }

    public String getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(String blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public String getBlogTypeName() {
        return blogTypeName;
    }

    public void setBlogTypeName(String blogTypeName) {
        this.blogTypeName = blogTypeName;
    }

    public List<String> getBlogTagIds() {
        return blogTagIds;
    }

    public void setBlogTagIds(List<String> blogTagIds) {
        this.blogTagIds = blogTagIds;
    }

    public List<String> getBlogTagNames() {
        return blogTagNames;
    }

    public void setBlogTagNames(List<String> blogTagNames) {
        this.blogTagNames = blogTagNames;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
