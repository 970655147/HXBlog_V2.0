package com.hx.blog_v2.domain.validator;

import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import org.springframework.stereotype.Component;

/**
 * KeywordsValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:42 PM
 */
@Component
public class KeywordsValidator implements Validator<String> {

    @Override
    public Result validate(String form, Object extra) {
        for (int i = 0, len = form.length(); i < len; i++) {
            char ch = form.charAt(i);
            if (!isCharLegal(ch)) {
                return ResultUtils.failed(" 包含非法字符[非数字|字母|汉字] ! ");
            }
        }

        return ResultUtils.success("success");
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
        if (ch >= 0 && ch <= 31) {
            return false;
        }
        if (ch >= 34 && ch <= 47) {
            return false;
        }
        if (ch >= 58 && ch <= 64) {
            return false;
        }
        if (ch >= 91 && ch <= 96) {
            return false;
        }
        if (ch >= 123 && ch <= 127) {
            return false;
        }

        return true;
    }

}
