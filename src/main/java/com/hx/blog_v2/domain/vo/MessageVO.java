package com.hx.blog_v2.domain.vo;

import com.hx.blog_v2.domain.po.interf.LogisticalId;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;

import java.util.Date;

/**
 * 消息的一条记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class MessageVO implements LogisticalId<String> {

    private String id;
    private String senderId;
    private String senderName;
    private String receiverId;
    private String receiverName;
    private String type;
    private String subject;
    private String content;
    private int consumed;
    private String createdAt;
    private String updatedAt;

    public MessageVO() {
        Date now = new Date();
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        consumed = 0;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getConsumed() {
        return consumed;
    }

    public void setConsumed(int consumed) {
        this.consumed = consumed;
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

    @Override
    public String logisticalId() {
        return senderId;
    }
}
