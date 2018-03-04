package com.hx.blog_v2.biz_handler.validator;

import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.biz_handler.interf.BizValidator;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;

/**
 * AlwaysFalseValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 8:18 PM
 */
public class AlwaysFalseValidator implements BizValidator {

    @Override
    public Result validate(BizContext context) {
        return ResultUtils.failed();
    }
}
