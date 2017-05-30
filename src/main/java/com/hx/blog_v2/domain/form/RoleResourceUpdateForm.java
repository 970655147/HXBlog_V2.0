package com.hx.blog_v2.domain.form;

/**
 * UserRoleUpdateForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/30/2017 7:14 PM
 */
public class RoleResourceUpdateForm {

    private String roleId;
    private String roleName;
    private String resourceIds;

    public RoleResourceUpdateForm() {
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
