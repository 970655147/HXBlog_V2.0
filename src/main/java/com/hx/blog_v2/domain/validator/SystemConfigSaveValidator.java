package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.SystemConfigSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
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
public class SystemConfigSaveValidator implements Validator<SystemConfigSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private EntityValueValidator entityValueValidator;
    @Autowired
    private EntityDescValidator entityDescValidator;
    @Autowired
    private ConfigTypeValidator configTypeValidator;
    @Autowired
    private SortValidator sortValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(SystemConfigSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = entityValueValidator.validate(form.getValue(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = entityDescValidator.validate(form.getDesc(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = configTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " type 不合法 ! ");
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
