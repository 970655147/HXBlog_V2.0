package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.LogSearchForm;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
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
public class LogSearchValidator implements Validator<LogSearchForm> {

    @Autowired
    private MysqlKeywordsValidator mysqlKeywordsValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;

    @Override
    public Result validate(LogSearchForm form, Object extra) {
        if (!Tools.isEmpty(form.getUrl())) {
            if (!mysqlKeywordsValidator.validate(form.getUrl(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " url 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getHandler())) {
            if (!mysqlKeywordsValidator.validate(form.getHandler(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " handler 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getParams())) {
            if (!mysqlKeywordsValidator.validate(form.getParams(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " params 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getName())) {
            if (!mysqlKeywordsValidator.validate(form.getName(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " name 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getEmail())) {
            if (!mysqlKeywordsValidator.validate(form.getEmail(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 输入不合法 ");
            }
        }
        if (form.getIsSystemUser() != null) {
            if (!intableBooleanValidator.validate(form.getIsSystemUser(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " isSystem 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getRequestIp())) {
            if (!mysqlKeywordsValidator.validate(form.getRequestIp(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 输入不合法 ");
            }
        }
        if (!Tools.isEmpty(form.getMsg())) {
            if (!mysqlKeywordsValidator.validate(form.getMsg(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " msg 输入不合法 ");
            }
        }

        return ResultUtils.success();

    }
}
