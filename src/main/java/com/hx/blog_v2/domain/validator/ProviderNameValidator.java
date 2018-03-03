package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ProviderNameValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 7:37 PM
 */
@Component
public class ProviderNameValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    @Autowired
    private VisibleValidator visibleValidator;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;

    @Override
    public Result doValidate(String name, Object extra) {
        if (Tools.isEmpty(name)) {
            return ResultUtils.failed(" provider 为空 !");
        }
        if ((name.length() < minLen) || (name.length() > maxLen)) {
            return ResultUtils.failed(" provider 长度不在范围内 !");
        }
        Result result = visibleValidator.validate(name, extra);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return minLen < 0;
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("entity.name.min.length", "1"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("entity.name.max.length", "64"));
    }
}
