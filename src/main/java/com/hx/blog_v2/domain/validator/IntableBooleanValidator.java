package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class IntableBooleanValidator implements Validator<Integer> {

    @Override
    public Result validate(Integer form, Object extra) {
        if (form == null) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的输入不合法 ! ");
        }
        if ((!Integer.valueOf(1).equals(form)) && (!Integer.valueOf(0).equals(form))) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的输入不合法 ! ");
        }

        return ResultUtils.success();

    }
}
