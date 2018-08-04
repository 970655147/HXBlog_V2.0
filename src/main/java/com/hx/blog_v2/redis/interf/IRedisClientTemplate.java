package com.hx.blog_v2.redis.interf;

import redis.clients.jedis.ShardedJedis;

import java.util.Map;
import java.util.Set;

/**
 * IRedisClientTemplate
 *
 * @author Jerry.X.He
 * @date 2018/3/1 15:11
 */
public interface IRedisClientTemplate {

    /**
     * 设置单个值
     *
     * @param key   key
     * @param value value
     * @return
     */
    String set(String key, String value);

    /**
     * 获取单个值
     *
     * @param key key
     * @return
     */
    String get(String key);

    /**
     * 设置hash
     *
     * @param key     key
     * @param hashMap hashMap
     * @return
     */
    String hmSet(String key, Map<String, String> hashMap);

    /**
     * 设置hash
     *
     * @param key       key
     * @param hashKey   hashKey
     * @param hashValue hashValue
     * @return
     */
    Long hSet(String key, String hashKey, String hashValue);

    /**
     * 获取hash-all
     *
     * @param key key
     * @return
     */
    Map<String, String> hGetAll(String key);

    /**
     * 获取hash-get
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/6/12 17:03
     */
    String hGet(String key, String keyInHash);

    /**
     * 删除单个键值对
     *
     * @param key key
     * @return
     */
    Long del(String key);

    /**
     * 设置单个值
     *
     * @param key     key
     * @param seconds seconds
     * @return
     */
    Long expire(String key, int seconds);

    /**
     * 获取所有的键
     *
     * @return
     */
    Set<String> getAllKeys();

    /**
     * 获取匹配给定 pattern 匹配的所有的键
     *
     * @return
     */
    Set<String> getAllKeys(String pattern);

    /**
     * 获取所有payment缓存键
     *
     * @param key key
     * @return
     */
    boolean exists(String key);

    /**
     * 给定的 key 对应的 value 递增
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:14
     */
    Long incr(String key);

    /**
     * 给定的 key 对应的 value 递减
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:14
     */
    Long decr(String key);

    /**
     * 给定的 key 对应的 value 递增给定的偏移
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:14
     */
    Long incrBy(String key, long inc);

    /**
     * 给定的 key 对应的 value 递增给定的偏移
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:14
     */
    Long decrBy(String key, long inc);

    /**
     * 获取一个 jedis 链接
     *
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:01
     */
    ShardedJedis getResource();

    /**
     * 获取给定的key 存储在redis中实际的key[prefix + key]
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:00
     */
    String getKey(String key);

    /**
     * 获取给定的key 去掉给定的前缀
     *
     * @param key key
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:00
     */
    String trimPrefix(String key);

    /**
     * 使用给定的 callback 处理业务, 然后返回结果数据
     *
     * @param callback callback
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:03
     */
    <T> T doWithCallback(IRedisClientCallback<T> callback);


}