package com.hx.blog_v2.domain.form;

import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.util.BizUtils;

/**
 * CommentSaveForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 7:44 PM
 */
public class CommentSaveForm implements UserInfoExtractor {

    /**
     * 回复的评论的id
     */
    private String id;
    private String blogId;
    private String floorId;
    private String commentId;
    private String name;
    private String email;
    private String headImgUrl;
    private String requestIp;
    private String ipFromSohu;
    private String ipAddr;
    private String toUser;
    private String comment;

    public CommentSaveForm() {
        requestIp = BizUtils.getIp();
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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getRequestIp() {
        if (requestIp.equals(ipFromSohu)) {
            return requestIp;
        }

        if(BizUtils.isLocalIp(requestIp)) {
            return ipFromSohu;
        }
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getIpFromSohu() {
        return ipFromSohu;
    }

    public void setIpFromSohu(String ipFromSohu) {
        this.ipFromSohu = ipFromSohu;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
