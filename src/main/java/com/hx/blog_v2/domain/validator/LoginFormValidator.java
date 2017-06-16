package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.LoginForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
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
public class LoginFormValidator implements Validator<LoginForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private VisibleValidator visibleValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private CheckCodeValidator checkCodeValidator;
    @Autowired
    private IpValidator ipValidator;

    @Override
    public Result validate(LoginForm form, Object extra) {
        Result errResult = regexWValidator.validate(form.getUserName(), extra);
        if (!errResult.isSuccess()) {
            errResult = emailValidator.validate(form.getUserName(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " username 格式不正确 ! ");
            }
        }
        errResult = visibleValidator.validate(form.getPassword(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " password 格式不正确 ! ");
        }
        errResult = checkCodeValidator.validate(form.getCheckCode(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " checkCode 格式不合法 ! ");
        }
        errResult = ipValidator.validate(form.getIp(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " ip 格式不合法 ! ");
        }
        errResult = visibleValidator.validate(form.getIpAddr(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " ip地址 格式不合法 ! ");
        }

        return ResultUtils.success();

    }
}
