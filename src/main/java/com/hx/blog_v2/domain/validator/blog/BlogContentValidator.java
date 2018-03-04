package com.hx.blog_v2.domain.validator.blog;

import com.hx.blog_v2.domain.validator.interf.ConfigRefreshableValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.stereotype.Component;

/**
 * BlogContentValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class BlogContentValidator extends ConfigRefreshableValidator<String> implements Validator<String> {

    /**
     * 最小长度
     */
    private int minLen = -1;
    private int maxLen = -1;

    @Override
    public Result doValidate(String form, Object extra) {
        if (Tools.isEmpty(form)) {
            return ResultUtils.failed(" content 为空 !");
        }
        if ((form.length() < minLen) || (form.length() > maxLen)) {
            return ResultUtils.failed(" content 长度不在范围内 !");
        }

        return ResultUtils.success();
    }

    @Override
    public boolean needRefresh() {
        return minLen < 0;
    }

    @Override
    public void refreshConfig() {
        minLen = Integer.parseInt(constantsContext.ruleConfig("content.blog.min.length", "3"));
        maxLen = Integer.parseInt(constantsContext.ruleConfig("content.blog.max.length", "10000000"));
    }
}
