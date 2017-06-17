package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
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
public class EmailValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    private Pattern pattern = null;

    @Override
    public Result doValidate(String email, Object extra) {
        if (Tools.isEmpty(email)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 为空 !");
        }
        if ((email.length() < minLen) || (email.length() > maxLen)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 长度不在范围内 !");
        }
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 的格式不正确 !");
        }

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return (minLen < 0) || (pattern == null);
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("email.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("email.max.length", "64"));
        String emailPatStr = constantsContext.ruleConfig("email.validate.pattern", "(\\w+)@(\\w+)(\\.\\w+)+");
        pattern = Pattern.compile(emailPatStr);
    }
}
