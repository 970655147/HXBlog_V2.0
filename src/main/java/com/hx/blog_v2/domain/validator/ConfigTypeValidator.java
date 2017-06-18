package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.ConfigType;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class ConfigTypeValidator implements Validator<String> {

    @Override
    public Result validate(String form, Object extra) {
        if (Tools.isEmpty(form)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " configType 不合法 ! ");
        }
        if (ConfigType.of(form) == null) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " configType 不存在 ! ");
        }

        return ResultUtils.success();

    }
}
