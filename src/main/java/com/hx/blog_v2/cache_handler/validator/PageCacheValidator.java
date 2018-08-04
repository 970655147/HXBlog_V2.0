package com.hx.blog_v2.cache_handler.validator;


import com.hx.blog_v2.cache_handler.CacheType;
import com.hx.blog_v2.cache_handler.anno.CacheHandle;
import com.hx.blog_v2.cache_handler.interf.CacheContext;
import com.hx.blog_v2.cache_handler.interf.CacheRequest;
import com.hx.blog_v2.cache_handler.interf.CacheValidator;
import com.hx.blog_v2.util.ResultUtils;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.json.JSONObject;
import com.hx.log.util.Log;

/**
 * PageCacheValidator
 *
 * @author Jerry.X.He
 * @date 2018/8/3 14:33
 */
public class PageCacheValidator implements CacheValidator {
    /**
     * 可以缓存的最大的页数
     */
    private int maxPageNo = 5;

    @Override
    public Result validate(CacheContext context) {
        CacheHandle cacheHandle = context.cacheHandle();
        if (cacheHandle == null) {
            return ResultUtils.success();
        }

        CacheType cacheType = cacheHandle.type();
        if (cacheType != CacheType.BASE_REQ) {
            return ResultUtils.success();
        }
        if (context.args().length == 0) {
            return ResultUtils.success();
        }

        CacheRequest params = (CacheRequest) context.args()[0];
        if (!params.isPageRequest()) {
            return ResultUtils.success();
        }
        Page pageEntity = params.getPageInfo();
        if (pageEntity == null) {
            Log.err(" 发现一个分页请求, 但是没有找到对应的分页信息, args : " + JSONObject.fromObject(params).toString());
            return ResultUtils.failed();
        }

        int pageNo = pageEntity.getPageNow();
        if (pageNo <= maxPageNo) {
            return ResultUtils.success();
        }
        return ResultUtils.failed();
    }

    public void setMaxPageNo(int maxPageNo) {
        this.maxPageNo = maxPageNo;
    }
}
