package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.ResourceSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.util.Tools;
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
public class ResourceSaveValidator implements Validator<ResourceSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private RelativeUrlValidator relativeUrlValidator;
    @Autowired
    private SortValidator sortValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;

    @Override
    public Result validate(ResourceSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = relativeUrlValidator.validate(form.getUrl(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if (!beanIdStrValidator.validate(form.getParentId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " parentId 不合法 ! ");
        }
        errResult = sortValidator.validate(form.getSort(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = intableBooleanValidator.validate(form.getEnable(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " enable 不合法 ! ");
        }

        return ResultUtils.success();

    }
}
