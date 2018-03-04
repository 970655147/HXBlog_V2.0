package com.hx.blog_v2.service.system;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.context.WebContext;
import com.hx.blog_v2.dao.interf.RequestLogDao;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.po.system.RequestLogPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.system.RequestLogService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * RequestLogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class RequestLogServiceImpl extends BaseServiceImpl<RequestLogPO> implements RequestLogService {

    @Autowired
    private RequestLogDao requestLogDao;
    @Autowired
    private ConstantsContext constantsContext;
    @Autowired
    private CacheContext cacheContext;

    @Override
    public void saveRequestLog(JoinPoint point, long cost) {
        HttpServletRequest req = WebContext.getRequest();
        String requestUri = req.getRequestURI();
        if (constantsContext.requestLogUriToIgnore.contains(requestUri)) {
            return;
        }

        cacheContext.todaysStatistics().incRequestLogCnt(1);
        cacheContext.now5SecStatistics().incRequestLogCnt(1);
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        boolean withUserInfoInServer = (user != null);
        if (!withUserInfoInServer) {
            user = SessionUser.DUMMY;
        }

        JSONObject params = JSONObject.fromObject(req.getParameterMap());
        cutParamsIfNeed(requestUri, params);
        String paramStr = String.valueOf(params);
        RequestLogPO po = new RequestLogPO(requestUri, String.valueOf(point.getSignature()),
                paramStr, cost, user.isSystemUser() ? 1 : 0);
        po.setUserInfo(user);
        if (!withUserInfoInServer) {
            po.setRequestIp(BizUtils.getIp());
        }
        requestLogDao.add(po);
    }

    /**
     * 如果需要 cut 参数的话, 处理 cut 参数的逻辑
     *
     * @param params params
     * @return void
     * @author Jerry.X.He
     * @date 6/25/2017 11:23 AM
     * @since 1.0
     */
    private void cutParamsIfNeed(String requestUri, JSONObject params) {
        if (constantsContext.paramsNeedToCut.contains(requestUri)) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String value = String.valueOf(entry.getValue());
                if (value.length() > constantsContext.paramsToCutMaxLen) {
                    params.put(entry.getKey(), value.substring(0, constantsContext.paramsToCutMaxLen) + "...");
                }
            }
        }
    }

}
