package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class SenseScoreValidator implements Validator<Integer> {

    @Override
    public Result validate(Integer form, Object extra) {
        if (form == null) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的输入不合法 ! ");
        }
        if ((form < 0) || (form > 5) ) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 给定的输入不合法 ! ");
        }

        return ResultUtils.success();

    }
}
