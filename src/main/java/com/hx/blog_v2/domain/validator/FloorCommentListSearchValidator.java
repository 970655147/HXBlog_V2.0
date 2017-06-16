package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.form.FloorCommentListSearchForm;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.common.util.ResultUtils;
import com.hx.log.str.StringUtils;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class FloorCommentListSearchValidator implements Validator<FloorCommentListSearchForm> {

    @Override
    public Result validate(FloorCommentListSearchForm form, Object extra) {
        if (!StringUtils.isNumeric(form.getBlogId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " blogId 不是数字 ! ");
        }
        if (!StringUtils.isNumeric(form.getFloorId())) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " floorId 不是数字 ! ");
        }

        return ResultUtils.success();

    }
}
