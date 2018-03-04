package com.hx.blog_v2.domain.validator.front_resources;

import com.hx.blog_v2.domain.form.front_resources.ImageSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ImageSearchValidator
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
        Result errResult = imageTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();

    }
}
