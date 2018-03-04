package com.hx.blog_v2.domain.validator.system;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.system.LoginForm;
import com.hx.blog_v2.domain.validator.others.PasswordValidator;
import com.hx.blog_v2.domain.validator.others.UserNameValidator;
import com.hx.blog_v2.domain.validator.message.EmailValidator;
import com.hx.blog_v2.domain.validator.common.IpAddrValidator;
import com.hx.blog_v2.domain.validator.common.IpValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * LoginFormValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class LoginFormValidator implements Validator<LoginForm> {

    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private PasswordValidator passwordValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private CheckCodeValidator checkCodeValidator;
    @Autowired
    private IpValidator ipValidator;
    @Autowired
    private IpAddrValidator ipAddrValidator;

    @Override
    public Result validate(LoginForm form, Object extra) {
        Result errResult = userNameValidator.validate(form.getUserName(), extra);
        if (!errResult.isSuccess()) {
            errResult = emailValidator.validate(form.getUserName(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " username 格式不正确 ! ");
            }
        }
        errResult = passwordValidator.validate(form.getPassword(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = checkCodeValidator.validate(form.getCheckCode(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = ipValidator.validate(form.getIp(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = ipAddrValidator.validate(form.getIpAddr(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();

    }
}
