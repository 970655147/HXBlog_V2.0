package com.hx.blog_v2.domain.form.front_resources;

import com.hx.blog_v2.domain.BaseForm;

/**
 * AdvListForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 5:29 PM
 */
public class AdvListForm extends BaseForm {

    /** url */
    private String url;

    public AdvListForm() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
