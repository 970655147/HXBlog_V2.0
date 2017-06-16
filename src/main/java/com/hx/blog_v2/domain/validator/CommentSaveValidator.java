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
    private RegexWValidator regexWValidator;
    @Autowired
    private MysqlKeywordsValidator mysqlKeywordsValidator;

    @Override
    public Result validate(CommentSaveForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getBlogId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        if (!Tools.isEmpty(form.getFloorId())) {
            if (!StringUtils.isNumeric(form.getFloorId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTypeId 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getCommentId())) {
            if (!StringUtils.isNumeric(form.getCommentId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTagId 非数字 ! ");
            }
        }

        if (!Tools.isEmpty(form.getName())) {
            Result result = userNameValidator.validate(form.getName(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户名格式不合法 ! ");
            }
        }
        Result result = userNameValidator.validate(form.getToUser(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户名格式不合法 ! ");
        }
        if (!Tools.isEmpty(form.getEmail())) {
            result = emailValidator.validate(form.getEmail(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " email 格式不合法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getRequestIp())) {
            result = ipValidator.validate(form.getRequestIp(), extra);
            if (!result.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 格式不合法 ! ");
            }
        }
        result = urlValidator.validate(form.getHeadImgUrl(), extra);
        if (!result.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 头像url 格式不合法 ! ");
        }

        // comment 的处理

        return ResultUtils.success();

    }
}
