package com.hx.blog_v2.domain.form.message;

import com.hx.blog_v2.domain.BaseForm;

/**
 * BlogSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/28/2017 11:09 AM
 */
public class MessageSearchForm extends BaseForm {

    private String id;
    private String senderId;
    private String receiverId;
    private String roleId;
    private String subject;
    private String content;

    public MessageSearchForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
