package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
import com.hx.blog_v2.util.BizUtils;
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
public class BlogSaveValidator implements Validator<BlogSaveForm> {

    @Autowired
    private EntityTitleValidator entityTitleValidator;
    @Autowired
    private UrlValidator urlValidator;
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

        Result errResult = entityTitleValidator.validate(form.getTitle(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = urlValidator.validate(form.getCoverUrl(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " coverUrl 格式不合法 ! ");
        }
        if (!beanIdStrValidator.validate(form.getBlogCreateTypeId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogCreateType 格式不合法 ! ");
        }
        if (!beanIdStrValidator.validate(form.getBlogTypeId(), extra).isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTypeId 格式不合法 ! ");
        }
        errResult = beanIdsValidator.validate(new BeanIdsForm(form.getBlogTagIds()), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 标签数据 格式不合法 ! ");
        }
        // 摘要使用一个 特殊的字符串, 来表示 默认提取摘要
        errResult = summaryValidator.validate(form.getSummary(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }
        errResult = blogContentValidator.validate(form.getContent(), extra);
        if (!errResult.isSuccess()) {
            return errResult;
        }

        String summaryFormatted = BizUtils.transferTags(form.getTitle() + "-" + form.getBlogTagIds(),
                form.getSummary(), constantsContext.forbiddenTagFormatMap);
        form.setSummary(summaryFormatted);
        String contentFormatted = BizUtils.prepareContent("[blog] " + form.getTitle() + "-" + form.getBlogTagIds(),
                form.getContent(), constantsContext.allowTagSensetiveTags, constantsContext.allowTagSensetiveTag2Attr,
                constantsContext.allowTagSensetiveAttrs);
        form.setContent(contentFormatted);
        return ResultUtils.success();

    }
}
