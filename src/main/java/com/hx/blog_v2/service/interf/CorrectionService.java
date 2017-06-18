package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.CorrectionSearchForm;
import com.hx.blog_v2.domain.form.DoCorrectionForm;
import com.hx.common.interf.common.Result;

/**
 * SystemService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/11/2017 9:43 AM
 */
public interface CorrectionService extends BaseService<Object> {

    /**
     * 获取给定的类型的 需要校正的数据列表
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result list(CorrectionSearchForm params);

    /**
     * 处理实际校正需要处理的业务
     *
     * @return
     * @author Jerry.X.He
     * @date 6/11/2017 9:45 AM
     * @since 1.0
     */
    Result doCorrection(DoCorrectionForm params);

}
