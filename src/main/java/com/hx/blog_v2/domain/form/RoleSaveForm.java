package com.hx.blog_v2.domain.form;

/**
 * 增加 mood 的时候的表单
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/22/2017 8:12 PM
 */
public class RoleSaveForm {

    private String id;
    private String name;
    private String desc;
    private int sort;
    private int enable;

    public RoleSaveForm() {
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
