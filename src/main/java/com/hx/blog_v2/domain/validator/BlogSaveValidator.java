package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.BeanIdsForm;
import com.hx.blog_v2.domain.form.BlogSaveForm;
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
public class BlogSaveValidator implements Validator<BlogSaveForm> {

    @Autowired
    private RegexWValidator regexWValidator;
    @Autowired
    private UrlValidator urlValidator;
    @Autowired
    private SummaryValidator summaryValidator;
    @Autowired
    private BlogContentValidator blogContentValidator;
    @Autowired
    private BeanIdsValidator beanIdsValidator;

    @Override
    public Result validate(BlogSaveForm form, Object extra) {
        if (!Tools.isEmpty(form.getId())) {
            if (!StringUtils.isNumeric(form.getId())) {
                return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " id 非数字 ! ");
            }
        }

        Result errResult = regexWValidator.validate(form.getTitle(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " title 包含敏感数据 ! ");
        }
        errResult = urlValidator.validate(form.getCoverUrl(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " coverUrl 格式不合法 ! ");
        }
        if (!StringUtils.isNumeric(form.getBlogTypeId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogTypeId 格式不合法 ! ");
        }
        errResult = beanIdsValidator.validate(new BeanIdsForm(form.getBlogTagIds()), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 标签数据 格式不合法 ! ");
        }
        // 摘要使用一个 特殊的字符串, 来表示 默认提取摘要
        errResult = summaryValidator.validate(form.getSummary(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 摘要数据 格式不合法 ! ");
        }
        errResult = blogContentValidator.validate(form.getContent(), extra);
        if (!errResult.isSuccess()) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " 博客内容 格式不合法 ! ");
        }

        return ResultUtils.success();

    }
}
