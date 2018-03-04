package com.hx.blog_v2.domain.vo.rlt;

/**
 * role -> resource
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 10:05 AM
 */
public class RltResourceInterfVO {

    private String id;
    private String resourceId;
    private String interfId;
    private String interfDesc;

    public RltResourceInterfVO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getInterfId() {
        return interfId;
    }

    public void setInterfId(String interfId) {
        this.interfId = interfId;
    }

    public String getInterfDesc() {
        return interfDesc;
    }

    public void setInterfDesc(String interfDesc) {
        this.interfDesc = interfDesc;
    }
}
