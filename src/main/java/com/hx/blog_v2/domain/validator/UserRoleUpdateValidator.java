package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.UserRoleUpdateForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.str.StringUtils;
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
public class UserRoleUpdateValidator implements Validator<UserRoleUpdateForm> {

    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private BeanIdsStrValidator beanIdsStrValidator;

    @Override
    public Result validate(UserRoleUpdateForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getUserId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " userId 非数字 ! ");
        }

        Result errResult = userNameValidator.validate(form.getUserName(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " userName 格式不正确 ! ");
        }
        errResult = beanIdsStrValidator.validate(form.getRoleIds(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 roleId 列表格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
