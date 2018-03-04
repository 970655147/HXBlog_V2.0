package com.hx.blog_v2.service.system;

import com.hx.blog_v2.biz_handler.handler.AuthUpdatedHandler;
import com.hx.blog_v2.context.CacheContext;
import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.system.SystemService;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SystemServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<Object> implements SystemService {

    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AuthUpdatedHandler authUpdatedHandler;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public Result statsSummary() {
        JSONObject data = new JSONObject().element("lastWeekInfo", cacheContext.recentlyStatistics())
                .element("todayInfo", cacheContext.todaysStatistics())
                .element("realTimeInfo", cacheContext.all5SecStatistics());
        return ResultUtils.success(data);
    }

    @Override
    public Result refreshAuthority() {
        authUpdatedHandler.refreshAuthority();
        return ResultUtils.success(" success ");
    }

}
