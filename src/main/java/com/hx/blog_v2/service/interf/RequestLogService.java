package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.po.RequestLogPO;
import org.aspectj.lang.JoinPoint;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface RequestLogService extends BaseService<RequestLogPO> {

    /**
     * 保存此次请求的日志信息
     *
     * @param point point
     * @param cost  cost
     * @return void
     * @author Jerry.X.He
     * @date 6/11/2017 8:14 PM
     * @since 1.0
     */
    void saveRequestLog(JoinPoint point, long cost);

}
