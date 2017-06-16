package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.ResourceInterfUpdateForm;
import com.hx.blog_v2.domain.form.RoleResourceUpdateForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
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
public class RoleResourceUpdateValidator implements Validator<RoleResourceUpdateForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private BeanIdsValidator beanIdsValidator;

    @Override
    public Result validate(RoleResourceUpdateForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getRoleId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " roleId 非数字 ! ");
        }

        Result errResult = regexWValidator.validate(form.getRoleName(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " roleName 格式不正确 ! ");
        }
        errResult = beanIdsValidator.validate(new BeanIdsForm(form.getResourceIds()), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 resourceIds 列表格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
