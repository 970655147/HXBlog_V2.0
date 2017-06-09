package com.hx.blog_v2.biz_handler.interf;

import com.hx.common.interf.common.Result;

/**
 * BizValidator
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/8/2017 9:00 PM
 */
public interface BizValidator {

    /**
     * 校验当前上下文的一些属性, 是否符合要求
     *
     * @return
     * @author Jerry.X.He
     * @date 6/8/2017 9:00 PM
     * @since 1.0
     */
    Result validate(BizContext context);

}
