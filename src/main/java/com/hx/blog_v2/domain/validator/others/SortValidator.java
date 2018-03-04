package com.hx.blog_v2.domain.validator.others;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.stereotype.Component;

/**
 * SortValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class SortValidator implements Validator<Integer> {

    @Override
    public Result validate(Integer sort, Object extra) {
        if(sort < 0) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sort 不合法 ! ");
        }

        return ResultUtils.success();
    }

}
