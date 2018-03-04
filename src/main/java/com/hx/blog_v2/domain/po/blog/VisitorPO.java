package com.hx.blog_v2.domain.po.blog;

import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
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
 * 项目的访问记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:14 AM
 */
public class VisitorPO implements JSONTransferable<VisitorPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"isSystemUser", "is_system_user"})
    private int isSystemUser;
    @JSONField({"requestIp", "request_ip"})
    private String requestIp;
    @JSONField({"ipFromSohu", "ip_from_sohu"})
    private String ipFromSohu;
    @JSONField({"ipAddr", "ip_addr"})
    private String ipAddr;
    @JSONField({"headerInfo", "header_info"})
    private String headerInfo;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public VisitorPO(String ipFromSohu, String ipAddr, int isSystemUser, String headerInfo) {
        this();
        this.ipFromSohu = ipFromSohu;
        this.ipAddr = ipAddr;
        this.isSystemUser = isSystemUser;
        this.headerInfo = headerInfo;
    }

    public VisitorPO() {
        createdAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
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

    public int getIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(int isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public String getIpFromSohu() {
        return ipFromSohu;
    }

    public void setIpFromSohu(String ipFromSohu) {
        this.ipFromSohu = ipFromSohu;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getHeaderInfo() {
        return headerInfo;
    }

    public void setHeaderInfo(String headerInfo) {
        this.headerInfo = headerInfo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static final VisitorPO PROTO_BEAN = new VisitorPO();

    @Override
    public VisitorPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(VisitorPO.class, this, config);
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
    public VisitorPO newInstance(Object... args) {
        return new VisitorPO();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void id(String id) {
        this.id = id;
    }

    public void setUserInfo(UserInfoExtractor extractor) {
        this.name = extractor.getName();
        this.email = extractor.getEmail();
        this.requestIp = extractor.getRequestIp();
    }

}
