package com.hx.blog_v2.domain.validator.blog;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.common.blog.BlogState;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.springframework.stereotype.Component;

/**
 * BlogStateValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class BlogStateValidator implements Validator<String> {

    @Override
    public Result validate(String form, Object extra) {
        if (Tools.isEmpty(form)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " configType 不合法 ! ");
        }
        if (BlogState.of(form) == null) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogState 不存在 ! ");
        }

        return ResultUtils.success();

    }
}
