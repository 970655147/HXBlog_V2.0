package com.hx.blog_v2.domain.vo;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 博客的回复
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:09 AM
 */
public class AdminCommentVO {

    private String id;
    private String blogId;
    private String blogName;
    private String floorId;
    private String commentId;

    private String name;
    private String email;
    private String headImgUrl;
    private String toUser;
    private String role;
    private String content;

    private String createdAt;

    public AdminCommentVO(String name, String email, String headImgUrl, String toUser, String role, String content) {
        this();
        this.name = name;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.toUser = toUser;
        this.role = role;
        this.content = content;
    }

    public AdminCommentVO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

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

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

}
