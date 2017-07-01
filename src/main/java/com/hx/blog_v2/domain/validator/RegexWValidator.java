package com.hx.blog_v2.domain.validator;

import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.stereotype.Component;

/**
 * KeywordsValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:42 PM
 */
@Component
public class RegexWValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 需要过滤的部分的, start, end 的pair
     */
    public static final int[] START_POS = {0, 34, 58, 91, 123};
    public static final int[] END_POS = {31, 47, 64, 96, 127};
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    /**
     * 特殊的排除在外的字符
     */
    private String specialExceptedChars;

    @Override
    public Result doValidate(String form, Object extra) {
        if (Tools.isEmpty(form)) {
            return ResultUtils.failed(" 输入为空 !");
        }
        if ((form.length() < minLen) || (form.length() > maxLen)) {
            return ResultUtils.failed(" 输入长度不在范围内 !");
        }

        for (int i = 0, len = form.length(); i < len; i++) {
            char ch = form.charAt(i);
            if (!isCharLegal(ch)) {
                return ResultUtils.failed(" 包含非法字符[非数字|字母|汉字] ! ");
            }
        }

        return ResultUtils.success("success");
    }

    @Override
    public boolean needRefresh() {
        return minLen < 0;
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("regex.w.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("regex.w.max.length", "1024"));
        specialExceptedChars = constantsContext.ruleConfig("regex.w.include.chars", ".$#&@[]{}_*");
    }

    /**
     * 判断给定的字符是否合法
     * 过滤特殊字符
     *
     * @param ch ch
     * @return boolean
     * @author Jerry.X.He
     * @date 6/15/2017 7:44 PM
     * @since 1.0
     */
    private boolean isCharLegal(char ch) {
        if(ch <= 128) {
            if(specialExceptedChars.indexOf(ch) >= 0) {
                return true;
            }
            for (int i = 0; i < START_POS.length; i++) {
                if ((ch >= START_POS[i]) && (ch <= END_POS[i])) {
                    return false;
                }
            }
        } else {
            if((ch < 0x4E00) || (ch > 0x9FA5) ) {
                return false;
            }
        }

        return true;
    }

}
