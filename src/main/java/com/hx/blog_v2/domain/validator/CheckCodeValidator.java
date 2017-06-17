package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.str.StringUtils;
import com.hx.log.util.Tools;
import org.springframework.stereotype.Component;

/**
 * UserNameValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class CheckCodeValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    private String checkCodeCandidatesStr = null;

    @Override
    public Result doValidate(String checkCode, Object extra) {
        if (Tools.isEmpty(checkCode)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " checkCode 为空 !");
        }
        if ((checkCode.length() < minLen) || (checkCode.length() > maxLen)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " checkCode 长度不在范围内 !");
        }

        for (int i = 0, len = checkCode.length(); i < len; i++) {
            char ch = checkCode.charAt(i);
            if (!StringUtils.containsChar(checkCodeCandidatesStr, ch)) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " checkCode 格式不正确 !");
            }
        }
        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return (minLen < 0) || (checkCodeCandidatesStr == null);
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("check_code.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("check_code.max.length", "6"));
        checkCodeCandidatesStr = constantsContext.checkCodeCandidatesStr;
    }
}
