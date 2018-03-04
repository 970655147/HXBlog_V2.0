package com.hx.blog_v2.domain.common.message;

/**
 * EmailAuthInfo
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/26/2017 9:09 PM
 */
public class EmailAuthInfo {

    private boolean auth;
    private String smtp;
    private String userName;
    private String password;

    public EmailAuthInfo(boolean auth, String smtp, String userName, String password) {
        this.auth = auth;
        this.smtp = smtp;
        this.userName = userName;
        this.password = password;
    }

    public EmailAuthInfo() {
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
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

}
