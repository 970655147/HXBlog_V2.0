package com.hx.blog_v2.domain.form.system;

import com.hx.blog_v2.domain.BaseForm;

/**
 * UpdatePwdForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/4/2017 7:12 PM
 */
public class UpdatePwdForm extends BaseForm {

    private String oldPwd;
    private String newPwd;

    public UpdatePwdForm() {
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
