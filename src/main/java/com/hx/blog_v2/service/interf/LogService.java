package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.form.LogSearchForm;
import com.hx.blog_v2.domain.vo.ExceptionLogVO;
import com.hx.blog_v2.domain.vo.RequestLogVO;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface LogService extends BaseService<Object> {

    /**
     * 搜索符合的条件的请求日志列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/28/2017 11:41 AM
     * @since 1.0
     */
    Result requestLogList(LogSearchForm params, Page<RequestLogVO> page);

    /**
     * 搜索符合的条件的异常日志列表
     *
     * @return result
     * @author Jerry.X.He
     * @date 5/28/2017 11:41 AM
     * @since 1.0
     */
    Result exceptionLogList(LogSearchForm params, Page<ExceptionLogVO> page);

}
