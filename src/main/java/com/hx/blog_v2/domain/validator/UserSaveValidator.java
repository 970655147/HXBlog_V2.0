package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.UserSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.str.StringUtils;
import com.hx.log.util.Tools;
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
public class UserSaveValidator implements Validator<UserSaveForm> {

    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private IpValidator ipValidator;
    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private VisibleValidator visibleValidator;

    @Override
    public Result validate(UserSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!StringUtils.isNumeric(form.getId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result result = userNameValidator.validate(form.getUserName(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " userName 格式不合法 ! ");
        }
        result = emailValidator.validate(form.getEmail(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 格式不合法 ! ");
        }
        result = regexWValidator.validate(form.getTitle(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " userTitle 格式不合法 ! ");
        }
        result = urlValidator.validate(form.getHeadImgUrl(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " headImgUrl 格式不合法 ! ");
        }
        if (!Tools.isEmpty(form.getMotto())) {
            result = visibleValidator.validate(form.getMotto(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " motto 格式不合法 ! ");
            }
        }

        return ResultUtils.success();
    }
}
