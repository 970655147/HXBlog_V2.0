package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.*;

/**
 * 博客顶踩的记录
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:12 AM
 */
public class BlogSensePO implements JSONTransferable<BlogSensePO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"blogId", "blog_id"})
    private String blogId;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"email", "email"})
    private String email;
    @JSONField({"sense", "sense"})
    private String sense;
    @JSONField({"clicked", "clicked"})
    private int clicked;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;

    public BlogSensePO(String blogId, String name, String email, String sense) {
        this();
        this.blogId = blogId;
        this.name = name;
        this.email = email;
        this.sense = sense;
    }

    public BlogSensePO() {
        this.createdAt = DateUtils.formate(new Date(), BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        clicked = 0;
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

    public String getSense() {
        return sense;
    }

    public void setSense(String sense) {
        this.sense = sense;
    }

    public int getClicked() {
        return clicked;
    }

    public void setClicked(int clicked) {
        this.clicked = clicked;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static final BlogSensePO PROTO_BEAN = new BlogSensePO();

    @Override
    public BlogSensePO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(BlogSensePO.class, this, config);
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
    public BlogSensePO newInstance(Object... args) {
        return new BlogSensePO();
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
