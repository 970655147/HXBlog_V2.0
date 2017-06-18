package com.hx.blog_v2.domain.validator;

import com.hx.blog_v2.domain.ErrorCode;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.interf.validator.Validator;
import com.hx.blog_v2.util.ResultUtils;
import org.springframework.stereotype.Component;

/**
 * AdminCommentSearchValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/15/2017 7:18 PM
 */
@Component
public class PageValidator implements Validator<Page> {

    @Override
    public Result validate(Page form, Object extra) {
        int pageNow = form.getPageNow();
        int pageSize = form.getPageSize();
        if (pageNow < 0) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " pageNow 必须 > 0 ");
        }
        if (pageSize < 0) {
            return ResultUtils.failed(ErrorCode.INPUT_NOT_FORMAT, " pageSize 必须 > 0 ");
        }

        return ResultUtils.success();
    }


}
