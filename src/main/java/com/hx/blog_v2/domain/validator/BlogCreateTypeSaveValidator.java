package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BlogCreateTypeSaveForm;
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
public class BlogCreateTypeSaveValidator implements Validator<BlogCreateTypeSaveForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private SortValidator sortValidator;

    @Override
    public Result validate(BlogCreateTypeSaveForm form, Object extra) {
        Result errResult = sortValidator.validate(form.getSort(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " sort 不合法 ! ");
        }

        if (!Tools.isEmpty(form.getId())) {
            if (!StringUtils.isNumeric(form.getId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }
        errResult = regexWValidator.validate(form.getName(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " name 包含敏感数据 ! ");
        }
        errResult = regexWValidator.validate(form.getDesc(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " desc 包含敏感数据 ! ");
        }
        errResult = urlValidator.validate(form.getImgUrl(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " url 格式不合法 ! ");
        }

        return ResultUtils.success();

    }
}
