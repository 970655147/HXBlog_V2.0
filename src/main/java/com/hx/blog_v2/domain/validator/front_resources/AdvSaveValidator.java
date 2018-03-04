package com.hx.blog_v2.domain.validator.front_resources;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.front_resources.AdvSaveForm;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.blog_v2.domain.validator.others.EntityNameValidator;
import com.hx.blog_v2.domain.validator.others.ProviderNameValidator;
import com.hx.blog_v2.domain.validator.others.SortValidator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AdvSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 3/3/2018 7:37 PM
 */
@Component
public class AdvSaveValidator implements Validator<AdvSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private ProviderNameValidator providerNameValidator;
    @Autowired
    private AdvTypeValidator advTypeValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private SortValidator sortValidator;

    @Override
    public Result validate(AdvSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = providerNameValidator.validate(form.getProvider(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = advTypeValidator.validate(form.getType(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        if(Tools.isEmpty(form.getPathMatch())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " pathMatch 不能为空 ! ");
        }
        // params 不做限制, 由前后端约定
        errResult = sortValidator.validate(form.getSort(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sort 不合法 ! ");
        }

        return ResultUtils.success();

    }
}
