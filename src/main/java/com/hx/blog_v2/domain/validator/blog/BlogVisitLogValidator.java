package com.hx.blog_v2.domain.validator.blog;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.blog.BlogVisitLogForm;
import com.hx.blog_v2.domain.validator.common.IpValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BlogVisitLogValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class BlogVisitLogValidator implements Validator<BlogVisitLogForm> {

    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private IpValidator ipValidator;

    @Override
    public Result validate(BlogVisitLogForm form, Object extra) {
        if (!beanIdStrValidator.validate(form.getBlogId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        Result errResult = ipValidator.validate(form.getRequestIp(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
