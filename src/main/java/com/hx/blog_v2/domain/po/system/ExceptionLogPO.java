package com.hx.blog_v2.domain.po.system;

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
 * 异常日志的历史记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:18 AM
 */
public class ExceptionLogPO implements JSONTransferable<ExceptionLogPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"url", "url"})
    private String url;
    @JSONField({"handler", "handler"})
    private String handler;
    @JSONField({"params", "params"})
    private String params;
    @JSONField({"headers", "headers"})
    private String headers;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"isSystemUser", "is_system_user"})
    private int isSystemUser;
    @JSONField({"requestIp", "request_ip"})
    private String requestIp;
    @JSONField({"msg", "msg"})
    private String msg;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public ExceptionLogPO(String url, String handler, String params, String headers, int isSystemUser, String msg) {
        this();
        this.url = url;
        this.handler = handler;
        this.params = params;
        this.headers = headers;
        this.isSystemUser = isSystemUser;
        this.msg = msg;
    }

    public ExceptionLogPO() {
        createdAt = DateUtils.format(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static final ExceptionLogPO PROTO_BEAN = new ExceptionLogPO();

    @Override
    public ExceptionLogPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(ExceptionLogPO.class, this, config);
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
    public ExceptionLogPO newInstance(Object... args) {
        return new ExceptionLogPO();
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
