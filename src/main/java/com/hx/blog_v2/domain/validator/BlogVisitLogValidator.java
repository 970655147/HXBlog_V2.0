package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BlogVisitLogForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.str.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class BlogVisitLogValidator implements Validator<BlogVisitLogForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private IpValidator ipValidator;

    @Override
    public Result validate(BlogVisitLogForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getBlogId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        Result errResult = ipValidator.validate(form.getRequestIp(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
