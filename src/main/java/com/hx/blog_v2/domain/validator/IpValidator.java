package com.hx.blog_v2.domain.validator;

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
public class IpValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    private Pattern pattern = null;

    @Override
    public Result doValidate(String ip, Object extra) {
        if (Tools.isEmpty(ip)) {
            return ResultUtils.failed(" ip 为空 !");
        }
        if ((ip.length() < minLen) || (ip.length() > maxLen)) {
            return ResultUtils.failed(" ip 长度不在范围内 !");
        }
        Matcher matcher = pattern.matcher(ip);
        if (!matcher.find()) {
            return ResultUtils.failed(" ip 的格式不正确 !");
        }

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return (minLen < 0) || (pattern == null);
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("ip.min.length", "7"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("ip.max.length", "24"));
        String patternStr = constantsContext.ruleConfig("ip.pattern_str", "(\\d+)(.\\d+){3,7}");
        pattern = Pattern.compile(patternStr);
    }
}
