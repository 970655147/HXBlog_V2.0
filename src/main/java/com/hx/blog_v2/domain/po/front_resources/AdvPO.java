package com.hx.blog_v2.domain.po.front_resources;

import com.hx.blog_v2.domain.BasePO;
import com.hx.blog_v2.domain.po.interf.LogisticalId;
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
 * AdvPO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:31 PM
 */
public class AdvPO extends BasePO implements JSONTransferable<AdvPO>, Comparable<AdvPO>, LogisticalId<String> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"provider", "provider"})
    private String provider;
    @JSONField({"pathMatch", "path_match"})
    private String pathMatch;
    @JSONField({"type", "type"})
    private String type;
    @JSONField({"params", "params"})
    private String params;
    @JSONField({"sort", "sort"})
    private int sort;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"deleted", "deleted"})
    private int deleted;

    public AdvPO(String name, String provider, String pathMatch, String type, String params, int sort) {
        this();
        this.name = name;
        this.provider = provider;
        this.pathMatch = pathMatch;
        this.type = type;
        this.params = params;
        this.sort = sort;
    }

    public AdvPO() {
        Date now = new Date();
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPathMatch() {
        return pathMatch;
    }

    public void setPathMatch(String pathMatch) {
        this.pathMatch = pathMatch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public static final AdvPO PROTO_BEAN = new AdvPO();

    @Override
    public AdvPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(AdvPO.class, this, config);
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
    public AdvPO newInstance(Object... args) {
        return new AdvPO();
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
    public int compareTo(AdvPO o) {
        int deltaSort = this.sort - o.sort;
        if (deltaSort != 0) {
            return deltaSort;
        }

        return this.name.compareTo(o.name);
    }

    @Override
    public String logisticalId() {
        return name;
    }
}
