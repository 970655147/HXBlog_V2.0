package com.hx.blog_v2.service.system;

import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.dao.interf.ExceptionLogDao;
import com.hx.blog_v2.domain.common.system.SessionUser;
import com.hx.blog_v2.domain.po.system.ExceptionLogPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.system.ExceptionLogService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.domain.BaseVO;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ExceptionLogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class ExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLogPO> implements ExceptionLogService {

    @Autowired
    private ExceptionLogDao exceptionLogDao;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public void saveExceptionLog(JoinPoint point, Result result, Throwable e) {
        HttpServletRequest req = WebContext.getRequest();
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        boolean withUserInfoInServer = (user != null);
        if (!withUserInfoInServer) {
            user = SessionUser.DUMMY;
        }

        cacheContext.todaysStatistics().incExceptionLogCnt(1);
        cacheContext.now5SecStatistics().incExceptionLogCnt(1);
        JSONObject params = JSONObject.fromObject(req.getParameterMap());
        if ((params != null) && (result != null)) {
            params.put("resultFromHandler", JSONObject.fromObject(result));
        }
        String requestUri = req.getRequestURI();
        cutParamsIfNeed(requestUri, params);
        String paramStr = String.valueOf(params);

        String handler = (point != null) ? String.valueOf(point.getSignature()) : "notBeHandledYet";
        String headerStr = String.valueOf(BizUtils.getHeaderInfo(req));
        String exceptionStr = String.valueOf(BizUtils.getExceptionInfo(e));
        int isSystemUser = user.isSystemUser() ? 1 : 0;
        ExceptionLogPO po = new ExceptionLogPO(req.getRequestURI(), handler,
                paramStr, headerStr, isSystemUser, exceptionStr);
        po.setUserInfo(user);
        if (!withUserInfoInServer) {
            po.setRequestIp(BizUtils.getIp());
        }
        exceptionLogDao.add(po);
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
