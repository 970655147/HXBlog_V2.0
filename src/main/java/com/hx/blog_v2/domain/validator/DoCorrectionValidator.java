package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.form.DoCorrectionForm;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CorrectionSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class DoCorrectionValidator implements Validator<DoCorrectionForm> {

    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private BeanIdsStrValidator beanIdsStrValidator;
    @Autowired
    private CorrectionTypeValidator correctionTypeValidator;

    @Override
    public Result validate(DoCorrectionForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            Result errResult = beanIdStrValidator.validate(form.getId(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(" 给定的id 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getIds())) {
            Result errResult = beanIdsStrValidator.validate(form.getIds(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(" 给定的ids 格式不正确 ! ");
            }
        }
        Result errResult = correctionTypeValidator.validate(form.getCode(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();

    }
}
