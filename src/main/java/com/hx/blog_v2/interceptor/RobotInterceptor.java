package com.hx.blog_v2.interceptor;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.common.Result;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 如果某用户 访问异常, 加入 黑名单, 关一天
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/25/2017 11:53 AM
 */
public class RobotInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        SessionUser user = addDummySessionIfNeed();
        String ip = user.getRequestIp();

        Cache<String, String> blackList = cacheContext.blackList();
        String msg = blackList.get(ip);
        if (!Tools.isEmpty(msg)) {
            Result result = ResultUtils.failed(ErrorCode.IN_BLANK_LIST, msg);
            WebContext.responseJson(result);
            return false;
        }


        int visitCnt = BizUtils.incFreq(cacheContext.inputNotFormatPerPeroid(), ip, 1);;
        if (visitCnt >= constantsContext.maxVisitCntPerPeriod) {
            blackList.put(ip, " you visit server too frequency, system is busy now ! ");
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String ip = user.getRequestIp();

        Object resultObj = WebContext.getAttributeFromRequest(BlogConstants.REQUEST_RESULT);
        boolean instanceOfResult = resultObj instanceof Result;
        int notFormatCnt = -1;
        // force cast if could
        if (instanceOfResult) {
            if (((Result) resultObj).getCode() == ErrorCode.INPUT_NOT_FORMAT.code()) {
                notFormatCnt = BizUtils.incFreq(cacheContext.inputNotFormatPerPeroid(), ip, 1);
            }
        }

        Cache<String, String> blackList = cacheContext.blackList();
        if (notFormatCnt >= constantsContext.maxNotFormatCntPerPeriod) {
            blackList.put(ip, " you visit server too frequency, system is busy now ! ");
        }
    }

    /**
     * 如果 当前用户的 session:user 为空, 创建一个 空的
     *
     * @return void
     * @author Jerry.X.He
     * @date 6/24/2017 7:00 PM
     * @since 1.0
     */
    private SessionUser addDummySessionIfNeed() {
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        if (user == null) {
            user = new SessionUser();
            user.setId(constantsContext.guestDummyId);
            user.setTitle(constantsContext.guestTitle);
            user.setRoleIds(constantsContext.guestRoles);
            user.setSystemUser(false);
            WebContext.setAttributeForSession(BlogConstants.SESSION_USER, user);
        }

        return user;
    }

}
