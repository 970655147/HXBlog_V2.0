package com.hx.blog_v2.service;

import com.hx.blog_v2.service.interf.BaseServiceImpl;
import com.hx.blog_v2.service.interf.SystemService;
import com.hx.blog_v2.context.CacheContext;
import com.hx.common.interf.common.Result;
import com.hx.common.util.ResultUtils;
import com.hx.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BlogServiceImpl
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 5/20/2017 11:47 AM
 */
@Service
public class SystemServiceImpl extends BaseServiceImpl<Object> implements SystemService {

    @Autowired
    private CacheContext cacheContext;

    @Override
    public Result statsSummary() {
        JSONObject data = new JSONObject().element("lastWeekInfo", cacheContext.allStatistics())
                .element("realTimeInfo", cacheContext.all5SecStatistics());
        return ResultUtils.success(data);
    }
}
