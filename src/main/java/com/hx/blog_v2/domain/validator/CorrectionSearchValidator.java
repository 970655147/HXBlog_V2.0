package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.form.CorrectionSearchForm;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
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
public class CorrectionSearchValidator implements Validator<CorrectionSearchForm> {

    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private CorrectionTypeValidator correctionTypeValidator;

    @Override
    public Result validate(CorrectionSearchForm form, Object extra) {
        Result errResult = correctionTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();

    }
}
