package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.ImageSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class ImageSearchValidator implements Validator<ImageSearchForm> {

    @Autowired
    private ImageTypeValidator imageTypeValidator;

    @Override
    public Result validate(ImageSearchForm form, Object extra) {
        if (!Tools.isEmpty(form.getType())) {
            Result errResult = imageTypeValidator.validate(form.getType(), extra);
            if (!errResult.isSuccess()) {
                return errResult;
            }
        }

        return ResultUtils.success();

    }
}
