package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
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
public class RoleResourceUpdateValidator implements Validator<RoleResourceUpdateForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private BeanIdsStrValidator beanIdsStrValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(RoleResourceUpdateForm form, Object extra) {
        if (!beanIdStrValidator.validate(form.getRoleId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " roleId 非数字 ! ");
        }

        Result errResult = entityNameValidator.validate(form.getRoleName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = beanIdsStrValidator.validate(form.getResourceIds(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 resourceIds 列表格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
