package com.hx.blog_v2.domain.validator.front_resources;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.front_resources.LinkSaveForm;
import com.hx.blog_v2.domain.validator.common.IntableBooleanValidator;
import com.hx.blog_v2.domain.validator.others.SortValidator;
import com.hx.blog_v2.domain.validator.common.UrlValidator;
import com.hx.blog_v2.domain.validator.common.BeanIdStrValidator;
import com.hx.blog_v2.domain.validator.others.EntityDescValidator;
import com.hx.blog_v2.domain.validator.others.EntityNameValidator;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * LinkSaveValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class LinkSaveValidator implements Validator<LinkSaveForm> {

    @Autowired
    private EntityNameValidator entityNameValidator;
    @Autowired
    private EntityDescValidator entityDescValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private SortValidator sortValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(LinkSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityNameValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = entityDescValidator.validate(form.getDesc(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = urlValidator.validate(form.getUrl(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
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
