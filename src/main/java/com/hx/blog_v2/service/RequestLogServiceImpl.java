package com.hx.blog_v2.service;

import com.hx.blog_v2.dao.interf.RequestLogDao;
import com.hx.blog_v2.domain.dto.SessionUser;
import com.hx.blog_v2.domain.po.RequestLogPO;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.RequestLogService;
import com.hx.blog_v2.util.BizUtils;
import com.hx.blog_v2.util.BlogConstants;
import com.hx.blog_v2.util.ConstantsContext;
import com.hx.blog_v2.util.WebContext;
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
public class RequestLogServiceImpl extends BaseServiceImpl<RequestLogPO> implements RequestLogService {

    @Autowired
    private RequestLogDao requestLogDao;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public void saveRequestLog(JoinPoint point, long cost) {
        HttpServletRequest req = WebContext.getRequest();
        String requestUri = req.getRequestURI();
        if (constantsContext.requestLogUriToIgnore.contains(requestUri)) {
            return;
        }

        SessionUser user = (SessionUser) WebContext.getAttributeFromSession(BlogConstants.SESSION_USER);
        boolean withUserInfoInServer = (user != null);
        if (!withUserInfoInServer) {
            user = SessionUser.DUMMY;
        }

        String paramStr = String.valueOf(JSONObject.fromObject(req.getParameterMap()));
        RequestLogPO po = new RequestLogPO(requestUri, String.valueOf(point.getSignature()),
                paramStr, cost, user.isSystemUser() ? 1 : 0);
        po.setUserInfo(user);
        if (!withUserInfoInServer) {
            po.setRequestIp(BizUtils.getIp());
        }
        requestLogDao.add(po);
    }


}
