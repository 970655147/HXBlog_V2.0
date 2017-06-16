package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.ResourceInterfUpdateForm;
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
public class ResourceInterfUpdateValidator implements Validator<ResourceInterfUpdateForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private BeanIdsValidator beanIdsValidator;

    @Override
    public Result validate(ResourceInterfUpdateForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getResourceId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " resourceId 非数字 ! ");
        }

        Result errResult = regexWValidator.validate(form.getResourceName(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " resourceName 格式不正确 ! ");
        }
        errResult = beanIdsValidator.validate(new BeanIdsForm(form.getInterfIds()), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 interfId 列表格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
