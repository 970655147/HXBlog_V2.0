package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BlogSaveForm;
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
public class BlogAuditValidator implements Validator<BlogSaveForm> {

    @Autowired
    private EntityTitleValidator entityTitleValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private BlogStateValidator blogStateValidator;
    @Autowired
    private SummaryValidator summaryValidator;
    @Autowired
    private BlogContentValidator blogContentValidator;
    @Autowired
    private BeanIdStrValidator beanIdStrValidator;
    @Autowired
    private BeanIdsValidator beanIdsValidator;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result validate(BlogSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!beanIdStrValidator.validate(form.getId(), extra).isSuccess()) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }
        Result errResult = blogStateValidator.validate(form.getState(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " state 格式不合法 ! ");
        }

        if (!Tools.isEmpty(form.getTitle())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getCoverUrl())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getBlogCreateTypeId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getBlogTypeId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getBlogTagIds())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getSummary())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        if (!Tools.isEmpty(form.getContent())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " bad format ! ");
        }
        return ResultUtils.success();

    }
}
