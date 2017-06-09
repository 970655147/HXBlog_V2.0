package com.hx.blog_v2.util;

import com.hx.common.interf.cache.Cache;
import com.hx.common.interf.cache.CacheContext;
import com.hx.common.interf.cache.CacheEntryFacade;
import com.hx.log.cache.CacheListenerAdapter;
import com.hx.log.json.interf.JSONTransferable;
import com.hx.log.util.Tools;
import com.hx.mongo.dao.interf.MysqlIBaseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * JSONTransferableCacheListener
 *
 * @author Jerry.X.He <970655147@qq.com>
 * @version 1.0
 * @date 6/9/2017 10:10 PM
 */
public class JSONTransferableCacheListener<T extends JSONTransferable<T>> extends CacheListenerAdapter<String, T> {

    private MysqlIBaseDao<T> dao;

    public JSONTransferableCacheListener(MysqlIBaseDao<T> dao) {
        Tools.assert0(dao != null, "'dao' can't be null !");
        this.dao = dao;
    }

    @Override
    public void afterEvict(CacheContext<String, T> context) {
        CacheEntryFacade<String, T> cacheEntry = context.cacheEntry();
        T po = cacheEntry.value();
        try {
            dao.updateById(po, BlogConstants.UPDATE_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beforeClear(CacheContext<String, T> context) {
        Cache<String, T> cache = context.getCache();
        if(cache.size() == 0) {
            return ;
        }

        List<T> eles = new ArrayList<>(cache.size());
        for (String key : cache.keys()) {
            T po = cache.get(key);
            if (po != null) {
                eles.add(po);
            }
        }

        try {
            dao.updateById(eles, BlogConstants.UPDATE_BEAN_CONFIG);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
