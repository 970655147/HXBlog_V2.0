package com.hx.blog_v2.domain.form;

/**
 * 编辑Tag的时候 的表单
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 6:19 PM
 */
public class BlogTagSaveForm {

    private String id;
    private String name;

    public BlogTagSaveForm() {
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
}
