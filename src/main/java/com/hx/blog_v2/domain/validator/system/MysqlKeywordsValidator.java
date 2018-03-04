package com.hx.blog_v2.domain.validator.system;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MysqlKeywordsValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:42 PM
 */
@Component
public class MysqlKeywordsValidator implements Validator<String> {

    @Autowired
    private ConstantsContext constantsContext;
    // mysql 的关键字符
    private String mysqlKeyChar = null;

    @Override
    public Result validate(String form, Object extra) {
        for (int i = 0, len = form.length(); i < len; i++) {
            char ch = form.charAt(i);
            if (!isCharLegal(ch)) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 包含非法字符[非数字|字母|汉字] ! ");
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
        if(mysqlKeyChar == null) {
            mysqlKeyChar = constantsContext.ruleConfig("mysql.key_char", "%*_'\"");
        }
        for (int i = 0, len = mysqlKeyChar.length(); i < len; i++) {
            if (ch == mysqlKeyChar.charAt(i)) {
                return false;
            }
        }

        return true;
    }

}
