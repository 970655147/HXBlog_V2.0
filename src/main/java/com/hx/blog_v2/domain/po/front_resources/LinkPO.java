package com.hx.blog_v2.domain.po.front_resources;

import com.hx.blog_v2.domain.BasePO;
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
 * 友情链接
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/24/2017 7:40 PM
 */
public class LinkPO extends BasePO implements JSONTransferable<LinkPO>, Comparable<LinkPO> {

    @JSONField({"id", "id"})
    private String id;
    @JSONField({"name", "name"})
    private String name;
    @JSONField({"desc", "desc"})
    private String desc;
    @JSONField({"url", "url"})
    private String url;
    @JSONField({"sort", "sort"})
    private int sort;
    @JSONField({"enable", "enable"})
    private int enable;
    @JSONField({"createdAt", "created_at"})
    private String createdAt;
    @JSONField({"updatedAt", "updated_at"})
    private String updatedAt;
    @JSONField({"deleted", "deleted"})
    private int deleted;

    public LinkPO(String name, String desc, String url, int sort, int enable) {
        this();
        this.name = name;
        this.desc = desc;
        this.url = url;
        this.sort = sort;
        this.enable = enable;
    }

    public LinkPO() {
        Date now = new Date();
        createdAt = DateUtils.format(now, BlogConstants.FORMAT_YYYY_MM_DD_HH_MM_SS);
        updatedAt = createdAt;
        enable = 1;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public static final LinkPO PROTO_BEAN = new LinkPO();

    @Override
    public LinkPO loadFromJSON(Map<String, Object> obj, JSONConfig config) {
        if (Tools.isEmpty(obj)) {
            return this;
        }

        JSONObject.fromObject(obj).toBean(LinkPO.class, this, config);
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
    public LinkPO newInstance(Object... args) {
        return new LinkPO();
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
    public int compareTo(LinkPO o) {
        int deltaSort = this.sort - o.sort;
        if(deltaSort != 0) {
            return deltaSort;
        }

        return this.name.compareTo(o.name);
    }

}
