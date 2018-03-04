package com.hx.blog_v2.domain.validator.rlt;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.common.BeanIdsForm;
import com.hx.blog_v2.domain.form.rlt.ResourceInterfUpdateForm;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdsValidator;
import com.hx.blog_v2.domain.validator.others.EntityNameValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ResourceInterfUpdateValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class ResourceInterfUpdateValidator implements Validator<ResourceInterfUpdateForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private BeanIdsValidator beanIdsValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(ResourceInterfUpdateForm form, Object extra) {
        if (!beanIdStrValidator.validate(form.getResourceId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " resourceId 非数字 ! ");
        }

        Result errResult = entityNameValidator.validate(form.getResourceName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = beanIdsValidator.validate(new BeanIdsForm(form.getInterfIds()), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 interfId 列表格式不正确 ! ");
        }

        return ResultUtils.success();

    }
}
