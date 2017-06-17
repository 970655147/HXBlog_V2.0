package com.hx.blog_v2.domain.po;

import com.hx.blog_v2.domain.po.interf.LogisticalId;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.DateUtils;
import com.hx.json.JSONObject;
import com.hx.json.config.interf.JSONConfig;
import com.hx.json.interf.JSONField;
import com.hx.log.alogrithm.tree.interf.TreeIdExtractor;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Constants;
import com.hx.log.util.Tools;

import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**
 * 心情
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:03 PM
 */
public class ResourcePO implements JSONTransferable<ResourcePO>, TreeIdExtractor<ResourcePO, String>,
        Comparable<ResourcePO>, LogisticalId<String> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"iconClass", "icon_class"})
    private String iconClass;
    @JSONField({"url", "url"})
    private String url;
    @JSONField({"sort", "sort"})
    private int sort;
    @JSONField({"parentId", "parent_id"})
    private String parentId;
    @JSONField({"level", "level"})
    private int level;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"enable", "enable"})
    private int enable;
    @JSONField({"deleted", "deleted"})
    private int deleted;

    public ResourcePO(String name, String iconClass, String url, String parentId, int sort,
                      int level, int enable) {
        this();
        this.name = name;
        this.iconClass = iconClass;
        this.url = url;
        this.parentId = parentId;
        this.sort = sort;
        this.level = level;
        this.enable = enable;
    }

    public ResourcePO() {
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

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public static final ResourcePO PROTO_BEAN = new ResourcePO();

    @Override
    public ResourcePO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(ResourcePO.class, this, config);
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
    public ResourcePO newInstance(Object... args) {
        return new ResourcePO();
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void id(String id) {
        this.id = id;
    }

    @Override
    public String parentId() {
        return parentId;
    }

    @Override
    public int compareTo(ResourcePO o) {
        int deltaSort = this.sort - o.sort;
        if(deltaSort != 0) {
            return deltaSort;
        }

        return this.name.compareTo(o.name);
    }

    @Override
    public String logisticalId() {
        return this.name;
    }
}
