package com.hx.blog_v2.domain.validator.system;

import com.hx.blog_v2.domain.form.system.CacheDetailForm;
import com.hx.blog_v2.domain.validator.interf.ConfigRefreshableValidator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CacheRemoveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class CacheRemoveValidator extends ConfigRefreshableValidator<CacheDetailForm> implements Validator<CacheDetailForm> {

    @Autowired
    private LocalCacheTypeValidator cacheTypeValidator;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;

    @Override
    public Result doValidate(CacheDetailForm form, Object extra) {
        Result errResult = cacheTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if(Tools.isEmpty(form.getId()) ) {
            return ResultUtils.failed(" id 不合法 ! ");
        }
        int len = form.getId().length();
        if(len < minLen || len > maxLen) {
            return ResultUtils.failed(" id 长度不合法 ! ");
        }

        return ResultUtils.success();

    }

    @Override
    public boolean needRefresh() {
        return minLen < 0;
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("entity.value.min.length", "1"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("entity.value.max.length", "1024"));
    }

}
