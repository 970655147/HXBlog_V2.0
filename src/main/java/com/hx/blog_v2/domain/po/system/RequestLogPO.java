package com.hx.blog_v2.domain.po.system;

import com.hx.blog_v2.domain.BasePO;
import com.hx.blog_v2.domain.form.interf.UserInfoExtractor;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
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
 * 请求日志的历史记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:16 AM
 */
public class RequestLogPO extends BasePO implements JSONTransferable<RequestLogPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"url", "url"})
    private String url;
    @JSONField({"handler", "handler"})
    private String handler;
    @JSONField({"params", "params"})
    private String params;
    @JSONField({"cost", "cost"})
    private long cost;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"isSystemUser", "is_system_user"})
    private int isSystemUser;
    @JSONField({"requestIp", "request_ip"})
    private String requestIp;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public RequestLogPO(String url, String handler, String params, long cost, int isSystemUser) {
        this();
        this.url = url;
        this.handler = handler;
        this.params = params;
        this.cost = cost;
        this.isSystemUser = isSystemUser;
    }

    public RequestLogPO() {
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

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static final RequestLogPO PROTO_BEAN = new RequestLogPO();

    @Override
    public RequestLogPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(RequestLogPO.class, this, config);
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
    public RequestLogPO newInstance(Object... args) {
        return new RequestLogPO();
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
