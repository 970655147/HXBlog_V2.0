package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.AdminCommentSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.str.StringUtils;
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
    private MysqlKeywordsValidator mysqlKeywordsValidator;

    @Override
    public Result validate(AdminCommentSearchForm form, Object extra) {
        if (!CollectionUtils.contains(AdminCommentSearchForm.SORT_TYPES, form.getSort())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 排序类型不正确 ! ");
        }

        if (!Tools.isEmpty(form.getBlogId())) {
            if (!StringUtils.isNumeric(form.getBlogId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getBlogTypeId())) {
            if (!StringUtils.isNumeric(form.getBlogTypeId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTypeId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getBlogTagId())) {
            if (!StringUtils.isNumeric(form.getBlogTagId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTagId 非数字 ! ");
            }
        }

        if (!Tools.isEmpty(form.getBlogName())) {
            if (!regexWValidator.validate(form.getBlogName(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogName 包含敏感数据 ! ");
            }
        }
        if (!Tools.isEmpty(form.getUserName())) {
            if (!regexWValidator.validate(form.getUserName(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " userName 包含敏感数据 ! ");
            }
        }
        if (!Tools.isEmpty(form.getToUserName())) {
            if (!regexWValidator.validate(form.getToUserName(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " toUserName 包含敏感数据 ! ");
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
