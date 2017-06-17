package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.ExceptionLogDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.po.ExceptionLogPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.ExceptionLogService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.context.WebContext;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class ExceptionLogServiceImpl extends BaseServiceImpl<ExceptionLogPO> implements ExceptionLogService {

    @Autowired
    private ExceptionLogDao exceptionLogDao;

    @Override
    public void saveExceptionLog(JoinPoint point, Result result, Throwable e) {
        HttpServletRequest req = WebContext.getRequest();
        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        boolean withUserInfoInServer = (user != null);
        if (!withUserInfoInServer) {
            user = SessionUser.DUMMY;
        }

        JSONObject params = JSONObject.fromObject(req.getParameterMap());
        if ((params != null) && (result != null)) {
            params.put("resultFromHandler", JSONObject.fromObject(result));
        }
        String handler = (point != null) ? String.valueOf(point.getSignature()) : "notBeHandledYet";
        String paramStr = String.valueOf(params);
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
}
