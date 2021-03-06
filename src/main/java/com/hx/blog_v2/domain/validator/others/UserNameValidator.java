package com.hx.blog_v2.domain.validator.others;

import com.hx.blog_v2.domain.validator.interf.ConfigRefreshableValidator;
import com.hx.blog_v2.domain.validator.common.RegexWValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserNameValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class UserNameValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    @Autowired
    private RegexWValidator regexWValidator;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;

    @Override
    public Result doValidate(String userName, Object extra) {
        if (Tools.isEmpty(userName)) {
            return ResultUtils.failed(" userName 为空 !");
        }
        if ((userName.length() < minLen) || (userName.length() > maxLen)) {
            return ResultUtils.failed(" userName 长度不在范围内 !");
        }
        Result result = regexWValidator.validate(userName, extra);
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
        minLen = Integer.parseInt(constantsContext.ruleConfig("user.name.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("user.name.max.length", "64"));
    }
}
