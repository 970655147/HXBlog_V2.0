package com.hx.blog_v2.domain.form;

/**
 * 增加用户的时候, 需要填的参数
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/23/2017 8:12 PM
 */
public class UserSaveForm {

    private String id;
    private String userName;
    private String password;
    private String nickName;
    private String title;
    private String email;
    private String headImgUrl;
    private String motto;

    public UserSaveForm() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

}
