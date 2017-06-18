package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BlogSenseForm;
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
public class BlogSenseValidator implements Validator<BlogSenseForm> {

    @Autowired
    private BlogNameValidator blogNameValidator;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private IpValidator ipValidator;
    @Autowired
    private SenseTypeValidator senseTypeValidator;
    @Autowired
    private IntableBooleanValidator intableBooleanValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(BlogSenseForm form, Object extra) {
        if (!beanIdStrValidator.validate(form.getBlogId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 非数字 ! ");
        }
        if (!Tools.isEmpty(form.getName())) {
            Result errResult = blogNameValidator.validate(form.getName(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户名不合法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getEmail())) {
            Result errResult = emailValidator.validate(form.getEmail(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 邮箱不合法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getHeadImgUrl())) {
            Result errResult = urlValidator.validate(form.getHeadImgUrl(), extra);
            if (!errResult.isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 用户头像不合法 ! ");
            }
        }

        Result errResult = ipValidator.validate(form.getRequestIp(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " requestIp 不合法 ! ");
        }
        errResult = senseTypeValidator.validate(form.getSense(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sense 不合法 ! ");
        }
        errResult = intableBooleanValidator.validate(form.getClicked(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " clicked 不合法 ! ");
        }

        return ResultUtils.success();

    }
}
