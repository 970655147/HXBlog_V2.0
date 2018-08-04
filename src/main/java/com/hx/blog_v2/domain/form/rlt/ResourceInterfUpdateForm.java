package com.hx.blog_v2.domain.form.rlt;

import com.hx.blog_v2.domain.BaseForm;

/**
 * UserRoleUpdateForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 7:14 PM
 */
public class ResourceInterfUpdateForm extends BaseForm {

    private String resourceId;
    private String resourceName;
    private String interfIds;

    public ResourceInterfUpdateForm() {
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getInterfIds() {
        return interfIds;
    }

    public void setInterfIds(String interfIds) {
        this.interfIds = interfIds;
    }
}
