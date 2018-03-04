package com.hx.blog_v2.domain.po.blog;

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
 * BlogVisitLogPO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 8:45 PM
 */
public class BlogVisitLogPO implements JSONTransferable<BlogVisitLogPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"blogId", "blog_id"})
    private String blogId;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"isSystemUser", "is_system_user"})
    private int isSystemUser;
    @JSONField({"requestIp", "request_ip"})
    private String requestIp;
    @JSONField({"createdAtDay", "created_at_day"})
    private String createdAtDay;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public BlogVisitLogPO(String blogId, String name, String email, int isSystemUser, String requestIp) {
        this();
        this.blogId = blogId;
        this.name = name;
        this.email = email;
        this.isSystemUser = isSystemUser;
        this.requestIp = requestIp;
    }

    public BlogVisitLogPO() {
        Date now = new Date();
        createdAtDay = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD);
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
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

    public String getCreatedAtDay() {
        return createdAtDay;
    }

    public void setCreatedAtDay(String createdAtDay) {
        this.createdAtDay = createdAtDay;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static final BlogVisitLogPO PROTO_BEAN = new BlogVisitLogPO();

    @Override
    public BlogVisitLogPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(BlogVisitLogPO.class, this, config);
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
    public BlogVisitLogPO newInstance(Object... args) {
        return new BlogVisitLogPO();
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
