package com.hx.blog_v2.local.service;

import com.hx.blog_v2.context.ConstantsContext;
import com.hx.blog_v2.redis.interf.IRedisClientTemplate;
import com.hx.common.cache.CacheService;
import com.hx.common.interf.common.Page;
import com.hx.common.interf.common.Result;
import com.hx.common.result.SimplePage;
import com.hx.common.result.SimpleResult;
import com.hx.common.util.ReflectUtils;
import com.hx.json.JSONArray;
import com.hx.json.JSONObject;
import com.hx.log.collection.CollectionUtils;
import com.hx.log.str.StringUtils;
import com.hx.log.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * LocalCacheService
 *
 * @author Jerry.X.He
 * @date 2018/7/12 11:30
 */
@Service
public class LocalCacheService implements CacheService {

    @Autowired
    private IRedisClientTemplate redisClientTemplate;
    @Autowired
    private ConstantsContext constantsContext;

    @Override
    public String set(String key, String value) {
        return redisClientTemplate.set(key, value);
    }

    @Override
    public String set(String key, String value, int expire) {
        String result = redisClientTemplate.set(key, value);
        redisClientTemplate.expire(key, expire);
        return result;
    }

    @Override
    public Long incr(String key) {
        return redisClientTemplate.incr(key);
    }

    @Override
    public Long decr(String key) {
        return redisClientTemplate.decr(key);
    }

    @Override
    public Long incrBy(String key, long inc) {
        return redisClientTemplate.incrBy(key, inc);
    }

    @Override
    public Long decrBy(String key, long inc) {
        return redisClientTemplate.decrBy(key, inc);
    }

    @Override
    public Long incr(String key, int expire) {
        Long result = redisClientTemplate.incr(key);
        redisClientTemplate.expire(key, expire);
        return result;
    }

    @Override
    public Long decr(String key, int expire) {
        Long result = redisClientTemplate.decr(key);
        redisClientTemplate.expire(key, expire);
        return result;
    }

    @Override
    public Long incrBy(String key, long inc, int expire) {
        Long result = redisClientTemplate.incrBy(key, inc);
        redisClientTemplate.expire(key, expire);
        return result;
    }

    @Override
    public Long decrBy(String key, long inc, int expire) {
        Long result = redisClientTemplate.decrBy(key, inc);
        redisClientTemplate.expire(key, expire);
        return result;
    }

    @Override
    public String get(String key) {
        return redisClientTemplate.get(key);
    }

    @Override
    public Long expire(String key, int expire) {
        return redisClientTemplate.expire(key, expire);
    }

    @Override
    public boolean exists(String key) {
        return redisClientTemplate.exists(key);
    }

    @Override
    public Long remove(String key) {
        return redisClientTemplate.del(key);
    }

    // --------------------------------------------------- 带上前缀 ----------------------------------------------------

    @Override
    public String set(String prefix, String key, String value) {
        return set(generateKeyByPrefix(prefix, key), value);
    }

    @Override
    public String set(String prefix, String key, String value, int expire) {
        return set(generateKeyByPrefix(prefix, key), value, expire);
    }

    @Override
    public Long incr(String prefix, String key) {
        return incr(generateKeyByPrefix(prefix, key));
    }

    @Override
    public Long decr(String prefix, String key) {
        return decr(generateKeyByPrefix(prefix, key));
    }

    @Override
    public Long incrBy(String prefix, String key, long incr) {
        return incrBy(generateKeyByPrefix(prefix, key), incr);
    }

    @Override
    public Long decrBy(String prefix, String key, long incr) {
        return decrBy(generateKeyByPrefix(prefix, key), incr);
    }

    @Override
    public Long incr(String prefix, String key, int expire) {
        return incr(generateKeyByPrefix(prefix, key), expire);
    }

    @Override
    public Long decr(String prefix, String key, int expire) {
        return decr(generateKeyByPrefix(prefix, key), expire);
    }

    @Override
    public Long incrBy(String prefix, String key, long incr, int expire) {
        return incrBy(generateKeyByPrefix(prefix, key), incr, expire);
    }

    @Override
    public Long decrBy(String prefix, String key, long incr, int expire) {
        return decrBy(generateKeyByPrefix(prefix, key), incr, expire);
    }

    @Override
    public String get(String prefix, String key) {
        return get(generateKeyByPrefix(prefix, key));
    }

    @Override
    public Long expire(String prefix, String key, int expire) {
        return expire(generateKeyByPrefix(prefix, key), expire);
    }

    @Override
    public boolean exists(String prefix, String key) {
        return exists(generateKeyByPrefix(prefix, key));
    }

    @Override
    public Long remove(String prefix, String key) {
        return remove(generateKeyByPrefix(prefix, key));
    }

    @Override
    public <T> T getOrNull(String key, Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        return typeCast(JSONObject.fromObject(cachedValue), clazz);
    }

    @Override
    public <T> T getOrNull(String prefix, String key, Class<T> clazz) {
        return getOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    @Override
    public <T> List<T> getListOrNull(String key, final Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        return cachedValueToList(cachedValue, clazz);
    }

    @Override
    public <T> List<T> getListOrNull(String prefix, String key, Class<T> clazz) {
        return getListOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    public <T> Page<T> getPageOrNull(String key, final Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        return cachedValueToPage(cachedValue, clazz);
    }

    public <T> Page<T> getPageOrNull(String prefix, String key, Class<T> clazz) {
        return getPageOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    @Override
    public <T> Result getResultOrNull(String key, Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        Result result = typeCast(cachedValue, SimpleResult.class);
        if (result != null) {
            Object data = typeCast(result.getData(), clazz);
            result.setData(data);
        }
        return result;
    }

    @Override
    public <T> Result getResultOrNull(String prefix, String key, Class<T> clazz) {
        return getResultOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    @Override
    public <T> Result getListResultOrNull(String key, Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        Result result = typeCast(cachedValue, SimpleResult.class);
        if (result != null) {
            String cachedPageValue = String.valueOf(result.getData());
            List<T> data = cachedValueToList(cachedPageValue, clazz);
            result.setData(data);
        }
        return result;
    }

    @Override
    public <T> Result getListResultOrNull(String prefix, String key, Class<T> clazz) {
        return getListResultOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    @Override
    public <T> Result getPageResultOrNull(String key, Class<T> clazz) {
        String cachedValue = get(key);
        if (StringUtils.isEmpty(cachedValue)) {
            return null;
        }

        Result result = typeCast(cachedValue, SimpleResult.class);
        if (result != null) {
            String cachedPageValue = String.valueOf(result.getData());
            Page<T> data = cachedValueToPage(cachedPageValue, clazz);
            result.setData(data);
        }
        return result;
    }

    @Override
    public <T> Result getPageResultOrNull(String prefix, String key, Class<T> clazz) {
        return getPageResultOrNull(generateKeyByPrefix(prefix, key), clazz);
    }

    /**
     * 将给定的对象[基本类型, json] 转换为给定的类型的对象
     *
     * @param object object
     * @param clazz  clazz
     * @return T
     * @author Jerry.X.He
     * @date 2018/8/3 15:28
     */
    public <T> T typeCast(Object object, Class<T> clazz) {
        if (clazz.isPrimitive()) {
            return clazz.cast(object);
        } else if (ReflectUtils.isPrimitiveWrapper(clazz)) {
            return clazz.cast(object);
        } else if (clazz == String.class) {
            return clazz.cast(object);
        } else if (clazz.isArray()) {

        } else if (Collection.class.isAssignableFrom(clazz)) {

        } else {
            JSONObject jsonObj = JSONObject.fromObject(object);
            for (String field : constantsContext.cacheHandleDetransferFields) {
                String value = jsonObj.optString(field);
                if (!StringUtils.isEmpty(value)) {
                    jsonObj.put(field, StringUtils.detransfer(value, constantsContext.cacheHandleDetransferChars));
                }
            }
            return JSONObject.toBean(jsonObj, clazz);
        }

        return null;
    }

    // ---------------------------------- 辅助方法 --------------------------------

    /**
     * 根据 prefix, key 生成 带前缀的 key
     *
     * @param prefix prefix
     * @param key    key
     * @return java.lang.String
     * @author Jerry.X.He
     * @date 2018/7/12 11:42
     */
    private final String generateKeyByPrefix(String prefix, String key) {
        return Tools.addIfNotEndsWith(prefix, ":") + key;
    }

    /**
     * 给定的缓存的 字符串 转换为 List<T>
     *
     * @param cachedValue cachedValue
     * @param clazz       clazz
     * @return java.util.List<T>
     * @author Jerry.X.He
     * @date 8/4/2018 11:44 AM
     * @since 1.0
     */
    private <T> List<T> cachedValueToList(String cachedValue, Class<T> clazz) {
        JSONArray arr = JSONArray.fromObject(cachedValue);
        if (CollectionUtils.isEmpty(arr)) {
            return null;
        }

        List<T> result = new ArrayList<>();
        for (int i = 0, len = arr.size(); i < len; i++) {
            T ele = typeCast(arr.getJSONObject(i), clazz);
            result.add(ele);
        }
        return result;
    }

    private <T> Page<T> cachedValueToPage(String cachedValue, Class<T> clazz) {
        JSONObject pageObj = JSONObject.fromObject(cachedValue);
        if (CollectionUtils.isEmpty(pageObj)) {
            return null;
        }

        SimplePage<T> result = typeCast(pageObj, SimplePage.class);
        JSONArray pageList = pageObj.getJSONArray("list");
        List<T> list = new ArrayList<>(pageList.size());
        for (int i = 0, len = pageList.size(); i < len; i++) {
            T ele = typeCast(pageList.getJSONObject(i), clazz);
            list.add(ele);
        }
        result.setList(list);
        return result;
    }


}
