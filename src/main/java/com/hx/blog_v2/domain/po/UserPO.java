package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * 用户
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/23/2017 8:12 PM
 */
public class UserPO implements JSONTransferable<UserPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"userName", "user_name"})
    private String userName;
    @JSONField({"pwdSalt", "pwd_salt"})
    private String pwdSalt;
    @JSONField({"password", "password"})
    private String password;
    @JSONField({"nickName", "nick_name"})
    private String nickName;
    @JSONField({"title", "title"})
    private String title;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"headImgUrl", "head_img_url"})
    private String headImgUrl;
    @JSONField({"motto", "motto"})
    private String motto;
    @JSONField({"lastLoginIp", "last_login_ip"})
    private String lastLoginIp;
    @JSONField({"lastLoginAt", "last_login_at"})
    private String lastLoginAt;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"deleted", "deleted"})
    private int deleted;

    public UserPO(String userName, String password, String nickName, String title, String email,
                  String headImgUrl, String motto) {
        this();
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.title = title;
        this.email = email;
        this.headImgUrl = headImgUrl;
        this.motto = motto;
    }

    public UserPO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        lastLoginAt = DateUtils.formate(new Date(0), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        deleted = 0;
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

    public String getPwdSalt() {
        return pwdSalt;
    }

    public void setPwdSalt(String pwdSalt) {
        this.pwdSalt = pwdSalt;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(String lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public static final UserPO PROTO_BEAN = new UserPO();

    @Override
    public UserPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(UserPO.class, this, config);
        return this;
    }

    @Override
    public JSONObject encapJSON(JSONConfig config) {
        return encapJSON(config, new LinkedList<>());
    }

    @Override
    public JSONObject encapJSON(JSONConfig config, Deque<Object> cycleDectector) {
        if (cycleDectector.contains(this)) {
            return JSONObject.fromObject(Constants.OBJECT_ALREADY_EXISTS).element("id", String.valueOf(id()));
        }
        cycleDectector.push(this);

        JSONObject result = JSONObject.fromObject(this, config);
        return result;
    }

    @Override
    public UserPO newInstance(Object... args) {
        return new UserPO();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void id(String id) {
        this.id = id;
    }
}
