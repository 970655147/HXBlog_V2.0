package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.MessageSaveForm;
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
public class MessageSaveValidator implements Validator<MessageSaveForm> {

    @Autowired
    private SummaryValidator summaryValidator;
    @Autowired
    private CommentContentValidator commentContentValidator;
    @Autowired
    private BeanIdsStrValidator beanIdsStrValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(MessageSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = beanIdsStrValidator.validate(form.getUserIds(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 userId 列表格式不正确 ! ");
        }
        errResult = beanIdsStrValidator.validate(form.getRoleIds(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 指定的 roleIds 列表格式不正确 ! ");
        }
        errResult = summaryValidator.validate(form.getSubject(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 主题不合法 ! ");
        }
        errResult = commentContentValidator.validate(form.getContent(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 消息内容不合法 ! ");
        }

        return ResultUtils.success();

    }
}
