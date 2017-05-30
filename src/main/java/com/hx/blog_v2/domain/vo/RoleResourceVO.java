package com.hx.blog_v2.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户角色关联信息
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 4:57 PM
 */
public class RoleResourceVO {

    /**
     * userId
     */
    private String id;
    private String name;
    private String desc;
    private String createdAt;
    private List<String> resourceIds;
    private List<String> resourceNames;

    public RoleResourceVO() {
        resourceIds = new ArrayList<>();
        resourceNames = new ArrayList<>();
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public List<String> getResourceNames() {
        return resourceNames;
    }

    public void setResourceNames(List<String> resourceNames) {
        this.resourceNames = resourceNames;
    }
}
