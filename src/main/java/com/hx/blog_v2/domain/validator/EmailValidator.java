package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.util.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UserNameValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class EmailValidator implements Validator<String> {

    @Autowired
    private KeywordsValidator keywordsValidator;
    @Autowired
    private ConstantsContext constantsContext;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    private Pattern pattern = null;

    @Override
    public Result validate(String email, Object extra) {
        if (Tools.isEmpty(email)) {
            return ResultUtils.failed(" email 为空 !");
        }
        initIfNeed();
        if (!((email.length() >= minLen) && (email.length() < maxLen))) {
            return ResultUtils.failed(" email 长度不在范围内 !");
        }
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return ResultUtils.failed(" email 的格式不正确 !");
        }

        return ResultUtils.success();
    }

    private void initIfNeed() {
        if (minLen < 0) {
            minLen = Integer.parseInt(constantsContext.ruleConfig("email.min.length", "3"));
            maxLen = Integer.parseInt(constantsContext.ruleConfig("email.max.length", "64"));
        }
        if (pattern == null) {
            String emailPatStr = constantsContext.ruleConfig("email.validate.pattern", "(\\w+)@(\\.\\w+)+)");
            pattern = Pattern.compile(emailPatStr);
        }
    }

}
