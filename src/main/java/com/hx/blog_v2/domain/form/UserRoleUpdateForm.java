package com.hx.blog_v2.domain.form;

/**
 * UserRoleUpdateForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 7:14 PM
 */
public class UserRoleUpdateForm {

    private String userId;
    private String userName;
    private String roleIds;

    public UserRoleUpdateForm() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}
