package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BlogSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
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
public class BlogSearchValidator implements Validator<BlogSearchForm> {

    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private MysqlKeywordsValidator mysqlKeywordsValidator;

    @Override
    public Result validate(BlogSearchForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getTypeId())) {
            if (!beanIdStrValidator.validate(form.getTypeId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " typeId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getTagId())) {
            if (!beanIdStrValidator.validate(form.getTagId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " getTagId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getKeywords())) {
            Result errResult = mysqlKeywordsValidator.validate(form.getKeywords(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 输入关键字不合法 ! ");
            }
        }

        return ResultUtils.success();

    }
}
