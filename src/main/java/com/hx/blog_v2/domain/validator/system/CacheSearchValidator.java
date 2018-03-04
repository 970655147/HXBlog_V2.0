package com.hx.blog_v2.domain.validator.system;

import com.hx.blog_v2.domain.form.system.CacheSearchForm;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CacheSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class CacheSearchValidator implements Validator<CacheSearchForm> {

    @Autowired
    private LocalCacheTypeValidator cacheTypeValidator;

    @Override
    public Result validate(CacheSearchForm form, Object extra) {
        Result errResult = cacheTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        return ResultUtils.success();

    }
}
