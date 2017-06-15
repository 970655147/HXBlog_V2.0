package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.CommentSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
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
public class CommentSaveValidator implements Validator<CommentSaveForm> {

    @Autowired
    private UserNameValidator userNameValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private IpValidator ipValidator;
    @Autowired
    private KeywordsValidator keywordsValidator;
    @Autowired
    private MysqlKeywordsValidator mysqlKeywordsValidator;

    @Override
    public Result validate(CommentSaveForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getBlogId()).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        if (!Tools.isEmpty(form.getFloorId())) {
            if (!StringUtils.isNumeric(form.getFloorId()).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTypeId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getCommentId())) {
            if (!StringUtils.isNumeric(form.getCommentId()).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTagId 非数字 ! ");
            }
        }

        if (!Tools.isEmpty(form.getName())) {
            Result result = userNameValidator.validate(form.getName(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getToUser())) {
            Result result = userNameValidator.validate(form.getToUser(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getEmail())) {
            Result result = emailValidator.validate(form.getEmail(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getRequestIp())) {
            Result result = ipValidator.validate(form.getRequestIp(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getIpFromSohu())) {
            Result result = ipValidator.validate(form.getIpFromSohu(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getHeadImgUrl())) {
            Result result = urlValidator.validate(form.getHeadImgUrl(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }
        if (!Tools.isEmpty(form.getIpAddr())) {
            Result result = keywordsValidator.validate(form.getIpAddr(), extra);
            if (!result.isSuccess()) {
                return result;
            }
        }

        // comment 的处理

        return ResultUtils.success();

    }
}
