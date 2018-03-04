package com.hx.blog_v2.domain.form.system;

/**
 * CorrectionSearchForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/18/2017 7:22 PM
 */
public class DoCorrectionForm {

    /**
     * 需要处理的业务关联的 id
     */
    public String id;
    public String ids;
    public String type;

    public DoCorrectionForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
