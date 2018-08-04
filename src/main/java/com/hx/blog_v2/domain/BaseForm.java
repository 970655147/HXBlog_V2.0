package com.hx.blog_v2.domain;

import com.hx.blog_v2.cache_handler.interf.CacheRequest;
import com.hx.common.interf.common.Page;
import com.hx.json.JSONObject;

/**
 * BaseForm
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 8/4/2018 10:26 AM
 */
public class BaseForm implements CacheRequest {

    @Override
    public String generateCacheKey() {
        return "baseForm";
    }

    @Override
    public boolean isPageRequest() {
        return false;
    }

    @Override
    public Page getPageInfo() {
        return null;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
