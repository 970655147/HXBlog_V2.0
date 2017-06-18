package com.hx.blog_v2.biz_handler.validator;

import com.hx.blog_v2.biz_handler.interf.BizContext;
import com.hx.blog_v2.biz_handler.interf.BizValidator;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;

import java.util.List;

/**
 * AlwaysTrueValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 8:18 PM
 */
public class AnyFalseValidator implements BizValidator {

    private BizValidator[] validators;

    public AnyFalseValidator(List<BizValidator> validators) {
        Tools.assert0(validators != null, "'validators' can't be null !");
        this.validators = validators.toArray(new BizValidator[validators.size()]);
    }

    @Override
    public Result validate(BizContext context) {
        for(BizValidator validator : validators) {
            Result result = validator.validate(context);
            if(! result.isSuccess()) {
                return result;
            }
        }

        return ResultUtils.success();
    }
}
