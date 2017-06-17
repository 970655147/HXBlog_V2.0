package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.MoodSaveForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
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
public class MoodSaveValidator implements Validator<MoodSaveForm> {
    
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private EntityTitleValidator entityTitleValidator;
    @Autowired
    private CommentContentValidator contentValidator;

    @Override
    public Result validate(MoodSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = entityTitleValidator.validate(form.getTitle(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = contentValidator.validate(form.getContent(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 内容不合法 ! ");
        }

        return ResultUtils.success();

    }
}
