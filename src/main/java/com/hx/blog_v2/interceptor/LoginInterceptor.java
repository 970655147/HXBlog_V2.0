package com.hx.blog_v2.interceptor;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.service.interf.ExceptionLogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.common.interf.common.Result;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * LoginInterceptor
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/19/2017 9:01 PM
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ExceptionLogService exceptionLogService;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if ((user == null) || (!user.isSystemUser())) {
            Result result = ResultUtils.failed(ErrorCode.NOT_LOGIN, " 您还没有登录, 或者登录过期, 请先登录 ! ");
            WebContext.responseJson(result);
            exceptionLogService.saveExceptionLog(null, result, null);
            return false;
        }
        /**
         * 强制下线相关业务
         */
        String userId = user.getId();
        String offlineReason = cacheContext.forceOffLine(userId);
        if (!Tools.isEmpty(offlineReason)) {
            Result result = ResultUtils.failed(ErrorCode.BE_OFFLINE, offlineReason + ", 您被强制下线了! 请重新登录 !");
            WebContext.responseJson(result);
            exceptionLogService.saveExceptionLog(null, result, null);
            removeInfoFromSession();
            return false;
        }

        return super.preHandle(request, response, handler);
    }

    /**
     * 移除用户的信息
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/17/2017 8:03 PM
     * @since 1.0
     */
    private void removeInfoFromSession() {
        HttpSession session = WebContext.getSession();
        session.removeAttribute(BlogConstants.SESSION_USER);
        session.removeAttribute(BlogConstants.SESSION_USER_ID);
        session.removeAttribute(BlogConstants.SESSION_CHECK_CODE);
    }

}
