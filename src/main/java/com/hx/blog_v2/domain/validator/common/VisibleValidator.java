package com.hx.blog_v2.domain.validator.common;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.stereotype.Component;

/**
 * VisibleValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:42 PM
 */
@Component
public class VisibleValidator implements Validator<String> {

    /**
     * 需要过滤的部分的, start, end 的pair
     */
    public static final int[] START_POS = {0};
    public static final int[] END_POS = {31};

    @Override
    public Result validate(String form, Object extra) {
        for (int i = 0, len = form.length(); i < len; i++) {
            char ch = form.charAt(i);
            if (!isCharLegal(ch)) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 包含非可视字符 ! ");
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
        if(ch <= 128) {
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
