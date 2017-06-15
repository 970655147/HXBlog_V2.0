package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.util.ConstantsContext;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * UserNameValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 8:25 PM
 */
@Component
public class UrlValidator implements Validator<String> {

    @Autowired
    private ConstantsContext constantsContext;
    /**
     * 最小长度, 最大长度
     */
    private int minLen = -1;
    private int maxLen = -1;
    private String mustContains = null;

    @Override
    public Result validate(String url, Object extra) {
        if (Tools.isEmpty(url)) {
            return ResultUtils.failed(" url 为空 !");
        }
        initIfNeed();
        if (!((url.length() >= minLen) && (url.length() < maxLen))) {
            return ResultUtils.failed(" url 长度不在范围内 !");
        }
        if (!url.contains(mustContains)) {
            return ResultUtils.failed(" url 格式不正确 !");
        }

        return ResultUtils.success();
    }

    private void initIfNeed() {
        if (minLen < 0) {
            minLen = Integer.parseInt(constantsContext.ruleConfig("url.min.length", "5"));
            maxLen = Integer.parseInt(constantsContext.ruleConfig("url.max.length", "2048"));
        }
        if (mustContains == null) {
            mustContains = constantsContext.ruleConfig("url.must_contains", "://");
        }
    }

}
