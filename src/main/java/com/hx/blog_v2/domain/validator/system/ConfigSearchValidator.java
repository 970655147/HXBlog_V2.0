package com.hx.blog_v2.domain.validator.system;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.system.SystemConfigSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ConfigSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class ConfigSearchValidator implements Validator<SystemConfigSaveForm> {

    @Autowired
    private ConfigTypeValidator configTypeValidator;

    @Override
    public Result validate(SystemConfigSaveForm form, Object extra) {
        Result errResult = configTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " configType 不存在 ! ");
        }

        return ResultUtils.success();

    }
}
