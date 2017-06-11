package com.hx.blog_v2.service.interf;

import com.hx.blog_v2.domain.po.ExceptionLogPO;
import com.hx.common.interf.common.Result;
import org.aspectj.lang.JoinPoint;

/**
 * BlogService
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:48 AM
 */
public interface ExceptionLogService extends BaseService<ExceptionLogPO> {

    /**
     * 保存异常记录信息
     *
     * @param point point
     * @param e     e
     * @return void
     * @author Jerry.X.He
     * @date 6/11/2017 8:53 PM
     * @since 1.0
     */
    void saveExceptionLog(JoinPoint point, Result result, Throwable e);

}
