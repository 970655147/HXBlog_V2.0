package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.MessageSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
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
public class MessageSearchValidator implements Validator<MessageSearchForm> {

    @Autowired
    private SummaryValidator summaryValidator;
    @Autowired
    private VisibleValidator visibleValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;

    @Override
    public Result validate(MessageSearchForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getSenderId())) {
            if (!beanIdStrValidator.validate(form.getSenderId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 发送者数据非法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getReceiverId())) {
            if (!beanIdStrValidator.validate(form.getReceiverId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 接收者数据非法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getRoleId())) {
            if (!beanIdStrValidator.validate(form.getRoleId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " role 非数字 ! ");
            }
        }
        if (!Tools.isEmpty(form.getSubject())) {
            if (!summaryValidator.validate(form.getSubject(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 主题数据非法 ! ");
            }
        }
        if (!Tools.isEmpty(form.getContent())) {
            if (!visibleValidator.validate(form.getContent(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 内容数据非法 ! ");
            }
        }

        return ResultUtils.success();

    }
}
