package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.util.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
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
public class UserNameValidator implements Validator<String> {

    @Autowired
    private KeywordsValidator keywordsValidator;
    @Autowired
    private ConstantsContext constantsContext;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;

    @Override
    public Result validate(String userName, Object extra) {
        if (Tools.isEmpty(userName)) {
            return ResultUtils.failed(" userName 为空 !");
        }
        initIfNeed();
        if (!((userName.length() >= minLen) && (userName.length() < maxLen))) {
            return ResultUtils.failed(" userName 长度不在范围内 !");
        }
        Result result = keywordsValidator.validate(userName, extra);
        if (!result.isSuccess()) {
            return result;
        }

        return ResultUtils.success();
    }

    private void initIfNeed() {
        if (minLen < 0) {
            minLen = Integer.parseInt(constantsContext.ruleConfig("username.min.length", "3"));
            maxLen = Integer.parseInt(constantsContext.ruleConfig("username.max.length", "16"));
        }
    }

}
