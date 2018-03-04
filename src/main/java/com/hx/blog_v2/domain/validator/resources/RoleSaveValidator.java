package com.hx.blog_v2.domain.validator.resources;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.resources.RoleSaveForm;
import com.hx.blog_v2.domain.validator.others.SortValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.blog_v2.domain.validator.others.EntityNameValidator;
import com.hx.blog_v2.domain.validator.common.IntableBooleanValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RoleSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class RoleSaveValidator implements Validator<RoleSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private SortValidator sortValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(RoleSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = sortValidator.validate(form.getSort(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sort 不合法 ! ");
        }
        errResult = intableBooleanValidator.validate(form.getEnable(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " enable 不合法 ! ");
        }

        return ResultUtils.success();

    }
}
