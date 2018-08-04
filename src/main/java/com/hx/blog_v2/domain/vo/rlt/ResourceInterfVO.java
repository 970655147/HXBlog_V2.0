package com.hx.blog_v2.domain.vo.rlt;

import com.hx.blog_v2.domain.BaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * ResourceInterfVO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 4:57 PM
 */
public class ResourceInterfVO extends BaseVO {

    /**
     * resourceId
     */
    private String id;
    private String name;
    private String url;
    private int level;
    private String parentId;
    private String createdAt;
    private List<String> interfIds;
    private List<String> interfNames;

    public ResourceInterfVO() {
        interfIds = new ArrayList<>();
        interfNames = new ArrayList<>();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getInterfIds() {
        return interfIds;
    }

    public void setInterfIds(List<String> interfIds) {
        this.interfIds = interfIds;
    }

    public List<String> getInterfNames() {
        return interfNames;
    }

    public void setInterfNames(List<String> interfNames) {
        this.interfNames = interfNames;
    }
}
