package com.hx.blog_v2.domain.vo;

import com.hx.json.interf.JSONField;

/**
 * AdvVO
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 6:04 PM
 */
public class AdvVO {

    private String name;
    private String provider;
    private String type;
    private String params;

    public AdvVO() {
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
}
