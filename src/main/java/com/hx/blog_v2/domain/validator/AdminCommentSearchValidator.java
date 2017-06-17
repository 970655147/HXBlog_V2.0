package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.collection.CollectionUtils;
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
public class AdminCommentSearchValidator implements Validator<AdminCommentSearchForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private BlogNameValidator blogNameValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private MysqlKeywordsValidator mysqlKeywordsValidator;

    @Override
    public Result validate(AdminCommentSearchForm form, Object extra) {
        if (!CollectionUtils.contains(AdminCommentSearchForm.SORT_TYPES, form.getSort())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 排序类型不正确 ! ");
        }

        if (!Tools.isEmpty(form.getBlogId())) {
            Result errResult = beanIdStrValidator.validate(form.getBlogId(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getBlogTypeId())) {
            Result errResult = beanIdStrValidator.validate(form.getBlogTypeId(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " typeId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getBlogTagId())) {
            Result errResult = beanIdStrValidator.validate(form.getBlogTagId(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " tagId 非数字 ! ");
            }
        }

        if (!Tools.isEmpty(form.getBlogName())) {
            Result errResult = blogNameValidator.validate(form.getBlogName(), extra);
            if (!errResult.isSuccess()) {
                return errResult;
            }
        }
        if (!Tools.isEmpty(form.getUserName())) {
            Result errResult = userNameValidator.validate(form.getUserName(), extra);
            if (!errResult.isSuccess()) {
                return errResult;
            }
        }
        if (!Tools.isEmpty(form.getToUserName())) {
            Result errResult = userNameValidator.validate(form.getToUserName(), extra);
            if (!errResult.isSuccess()) {
                return errResult;
            }
        }
        if (!Tools.isEmpty(form.getKeywords())) {
            if (!regexWValidator.validate(form.getKeywords(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " keywords 包含敏感数据 ! ");
            }
        }
        if (!Tools.isEmpty(form.getContent())) {
            if (!mysqlKeywordsValidator.validate(form.getContent(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " content 包含敏感数据 ! ");
            }
        }

        return ResultUtils.success();

    }
}
