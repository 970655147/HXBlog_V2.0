package com.hx.blog_v2.interceptor;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.domain.ErrorCode;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.service.interf.system.ExceptionLogService;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import com.hx.log.cache.mem.UniverseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 某些请求在一定的周期内限制多次访问
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 7/28/2017 8:17 PM
 */
@Component
public class ReqDelayInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private ExceptionLogService exceptionLogService;
    @Autowired
    private ConstantsContext constantsContext;

    /**
     * 用户, 请求 -> 上一次访问的时间
     */
    private Cache<String, Long> ipUri2LastVisit = new UniverseCache<>(true);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        JSONObject reqDelayMap = constantsContext.reqDelayMap;
        long minDelay = reqDelayMap.optLong(requestUri, -1);
        if (minDelay < 0) {
            return super.preHandle(request, response, handler);
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        String ip = user.getRequestIp();
        String key = ipUriKey(ip, requestUri);
        Long lastVisited = ipUri2LastVisit.get(key);
        if (lastVisited == null) {
            ipUri2LastVisit.put(key, System.currentTimeMillis());
            return super.preHandle(request, response, handler);
        }

        long delta = System.currentTimeMillis() - lastVisited;
        if (delta >= minDelay) {
            ipUri2LastVisit.put(key, System.currentTimeMillis());
            return super.preHandle(request, response, handler);
        }

        Result reqDelayResult = ResultUtils.failed(ErrorCode.REQ_DELAY_COOLING, " 系统繁忙, 请稍后重试 ! ");
        WebContext.responseJson(reqDelayResult);
        exceptionLogService.saveExceptionLog(null, reqDelayResult, null);
        return false;
    }

    /**
     * ip 和 uri 组成的一个 key
     *
     * @param ip  ip
     * @param uri uri
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 7/28/2017 8:45 PM
     * @since 1.0
     */
    private String ipUriKey(String ip, String uri) {
        return ip + "_" + uri;
    }

}
