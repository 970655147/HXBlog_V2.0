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
 * 博客创建类型
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:03 PM
 */
public class SystemConfigPO implements JSONTransferable<SystemConfigPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"value", "value"})
    private String value;
    @JSONField({"desc", "desc"})
    private String desc;
    @JSONField({"type", "type"})
    private String type;
    @JSONField({"sort", "sort"})
    private int sort;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"enable", "enable"})
    private int enable;
    @JSONField({"deleted", "deleted"})
    private int deleted;

    public SystemConfigPO(String name, String value, String desc, String type, int sort, int enable) {
        this();
        this.name = name;
        this.value = value;
        this.desc = desc;
        this.type = type;
        this.sort = sort;
        this.enable = enable;
    }

    public SystemConfigPO() {
        Date now = new Date();
        createdAt = DateUtils.formate(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        deleted = 0;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
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

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getDeleted() {
        return deleted;
    }

    public static final SystemConfigPO PROTO_BEAN = new SystemConfigPO();

    @Override
    public SystemConfigPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(SystemConfigPO.class, this, config);
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
    public SystemConfigPO newInstance(Object... args) {
        return new SystemConfigPO();
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
