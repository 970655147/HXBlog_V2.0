package com.hx.blog_v2.domain.validator.blog;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.blog.BlogTypeSaveForm;
import com.hx.blog_v2.domain.validator.others.EntityNameValidator;
import com.hx.blog_v2.domain.validator.others.SortValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * BlogTypeSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class BlogTypeSaveValidator implements Validator<BlogTypeSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private SortValidator sortValidator;

    @Override
    public Result validate(BlogTypeSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = sortValidator.validate(form.getSort(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sort 不合法 ! ");
        }

        return ResultUtils.success();

    }
}
