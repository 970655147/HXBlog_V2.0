package com.hx.blog_v2.domain.dto;

import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;

import java.util.Date;

/**
 * 存放在session中的用户信息[登录的, 非登录的]
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/25/2017 8:10 PM
 */
public class SessionUser implements UserInfoExtractor {

    /**
     * 一个缺省的用户信息
     */
    public static SessionUser DUMMY = new SessionUser("unknown", "unknown",
            "unknown", "guest", "", false);

    /**
     * userId
     */
    private String id;
    private String name;
    private String email;
    private String headImgUrl;
    private String requestIp;
    private String ipAddr;
    private String title;
    private String roleIds;
    private String loginDate;
    private boolean isSystemUser;

    public SessionUser(String name, String email, String headImgUrl, String title, String roleIds, boolean isSystemUser) {
        this();
        this.name = name;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.title = title;
        this.roleIds = roleIds;
        this.isSystemUser = isSystemUser;
    }

    public SessionUser() {
        this.requestIp = BizUtils.getIp();
        this.ipAddr = BizUtils.getIpAddr(this.requestIp);
        this.loginDate = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public boolean isSystemUser() {
        return isSystemUser;
    }

    public void setSystemUser(boolean systemUser) {
        isSystemUser = systemUser;
    }

    @Override
    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }
}
