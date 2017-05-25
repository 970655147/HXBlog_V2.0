package com.hx.blog_v2.domain.form;

/**
 * CommentSaveForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 7:44 PM
 */
public class CommentSaveForm {

    private String id;
    private String blogId;
    private String floorId;
    private String commentId;
    private String toUser;
    private String content;

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

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
