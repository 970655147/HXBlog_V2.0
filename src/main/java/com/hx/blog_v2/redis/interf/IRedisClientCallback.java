package com.hx.blog_v2.redis.interf;

import redis.clients.jedis.ShardedJedis;

/**
 * IRedisClientCallback
 *
 * @author Jerry.X.He
 * @date 2017/9/28 16:02
 */
public interface IRedisClientCallback<T> {

    /**
     * 使用给定的 jedis 处理业务
     *
     * @param jedis jedis
     * @return
     * @author Jerry.X.He
     * @date 2017/9/28 16:03
     */
    T run(ShardedJedis jedis);

}
