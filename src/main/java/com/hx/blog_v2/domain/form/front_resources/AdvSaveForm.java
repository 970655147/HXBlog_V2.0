package com.hx.blog_v2.domain.form.front_resources;

import com.hx.blog_v2.domain.BaseForm;
import com.hx.json.interf.JSONField;

/**
 * AdvSaveForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 7:26 PM
 */
public class AdvSaveForm extends BaseForm {

    private String id;
    private String name;
    private String provider;
    private String pathMatch;
    private String type;
    private String params;
    private int sort;

    public AdvSaveForm() {
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
}
