package com.hx.blog_v2.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关联信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 4:57 PM
 */
public class UserRoleVO {

    /**
     * userId
     */
    private String id;
    private String userName;
    private String nickName;
    private String email;
    private String headImgUrl;
    private String createdAt;
    private List<String> roleIds;
    private List<String> roleNames;

    public UserRoleVO() {
        roleIds = new ArrayList<>();
        roleNames = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(List<String> roleNames) {
        this.roleNames = roleNames;
    }
}
