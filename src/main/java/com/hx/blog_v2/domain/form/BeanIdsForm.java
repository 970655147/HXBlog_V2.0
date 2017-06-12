package com.hx.blog_v2.domain.form;

/**
 * 包含逻辑上一系列的id的的参数
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 9:42 AM
 */
public class BeanIdsForm {

    private String ids;


    public BeanIdsForm(String ids) {
        this.ids = ids;
    }

    public BeanIdsForm() {

    }


    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
