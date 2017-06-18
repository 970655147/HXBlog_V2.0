package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.UpdatePwdForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class UpdatePwdValidator implements Validator<UpdatePwdForm> {

    @Autowired
    private PasswordValidator passwordValidator;

    @Override
    public Result validate(UpdatePwdForm form, Object extra) {
        Result errResult = passwordValidator.validate(form.getOldPwd(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " oldPassword 格式不正确 ! ");
        }
        errResult = passwordValidator.validate(form.getNewPwd(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " newPassword 格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
