package com.hx.blog_v2.domain.form;

/**
 * 存放在session中的用户信息[登录的, 非登录的]
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/25/2017 8:10 PM
 */
public class SessionUser {

    private String userName;
    private String email;
    private String headImgUrl;
    private String role;

    public SessionUser(String userName, String email, String headImgUrl, String role) {
        this.userName = userName;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.role = role;
    }

    public SessionUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
