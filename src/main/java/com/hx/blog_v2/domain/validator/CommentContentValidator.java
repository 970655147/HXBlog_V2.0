package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
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
public class CommentContentValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 最小长度
     */
    private int minLen = -1, maxLen = -1;

    @Override
    public Result doValidate(String comment, Object extra) {
        if (Tools.isEmpty(comment)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " content 为空 !");
        }
        if ((comment.length() < minLen) || (comment.length() > maxLen)) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " content 长度不在范围内 !");
        }
        // TODO: 6/16/2017 内容的校验

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return minLen < 0;
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("content.comment.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("content.comment.max.length", "4096"));
    }
}
