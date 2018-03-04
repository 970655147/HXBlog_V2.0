package com.hx.blog_v2.domain.validator.front_resources;

import com.hx.blog_v2.domain.form.front_resources.AdvListForm;
import com.hx.blog_v2.domain.validator.common.UrlValidator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdvListValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class AdvListValidator implements Validator<AdvListForm> {

    @Autowired
    private UrlValidator urlValidator;

    @Override
    public Result validate(AdvListForm params, Object extra) {
        Result errResult = urlValidator.validate(params.getUrl(), extra);
        if(! errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();
    }

}
