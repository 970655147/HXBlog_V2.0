package com.hx.blog_v2.redis;

import com.hx.blog_v2.redis.interf.IRedisClientCallback;
import com.hx.blog_v2.redis.interf.IRedisClientTemplate;
import com.hx.log.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Map;
import java.util.Set;

public class RedisClientTemplate implements IRedisClientTemplate {

    /**
     * 默认的名称空间
     */
    private String redisPrefix = "hxblog:";
    /**
     * sharedJedisPool
     */
    private ShardedJedisPool shardedJedisPool;

    @Override
    public String set(final String key, final String value) {
        return doWithCallback(new IRedisClientCallback<String>() {
            @Override
            public String run(ShardedJedis jedis) {
                return jedis.set(getKey(key), value);
            }
        });
    }

    @Override
    public String get(final String key) {
        return doWithCallback(new IRedisClientCallback<String>() {
            @Override
            public String run(ShardedJedis jedis) {
                return jedis.get(getKey(key));
            }
        });
    }

    @Override
    public String hmSet(final String key, final Map<String, String> hash) {
        return doWithCallback(new IRedisClientCallback<String>() {
            @Override
            public String run(ShardedJedis jedis) {
                return jedis.hmset(getKey(key), hash);
            }
        });
    }

    @Override
    public Long hSet(final String key, final String hashKey, final String hashValue) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.hset(getKey(key), hashKey, hashValue);
            }
        });
    }

    @Override
    public Map<String, String> hGetAll(final String key) {
        return doWithCallback(new IRedisClientCallback<Map<String, String>>() {
            @Override
            public Map<String, String> run(ShardedJedis jedis) {
                return jedis.hgetAll(getKey(key));
            }
        });
    }

    @Override
    public String hGet(final String key, final String keyInHash) {
        return doWithCallback(new IRedisClientCallback<String>() {
            @Override
            public String run(ShardedJedis jedis) {
                return jedis.hget(getKey(key), keyInHash);
            }
        });
    }

    @Override
    public Long del(final String key) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.del(getKey(key));
            }
        });
    }

    @Override
    public Long expire(final String key, final int seconds) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.expire(getKey(key), seconds);
            }
        });
    }

    @Override
    public Set<String> getAllKeys() {
        return doWithCallback(new IRedisClientCallback<Set<String>>() {
            @Override
            public Set<String> run(ShardedJedis jedis) {
                return jedis.hkeys(getKey("*"));
            }
        });
    }

    @Override
    public Set<String> getAllKeys(String pattern) {
        final String patternInternalUse = pattern;
        return doWithCallback(new IRedisClientCallback<Set<String>>() {
            @Override
            public Set<String> run(ShardedJedis jedis) {
                return jedis.hkeys(getKey(patternInternalUse));
            }
        });
    }

    @Override
    public boolean exists(final String key) {
        return doWithCallback(new IRedisClientCallback<Boolean>() {
            @Override
            public Boolean run(ShardedJedis jedis) {
                return jedis.exists(getKey(key));
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.incr(getKey(key));
            }
        });
    }

    @Override
    public Long decr(final String key) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.decr(getKey(key));
            }
        });
    }

    @Override
    public Long incrBy(final String key, final long inc) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.incrBy(getKey(key), inc);
            }
        });
    }

    @Override
    public Long decrBy(final String key, final long inc) {
        return doWithCallback(new IRedisClientCallback<Long>() {
            @Override
            public Long run(ShardedJedis jedis) {
                return jedis.decrBy(getKey(key), inc);
            }
        });
    }

    @Override
    public ShardedJedis getResource() {
        return shardedJedisPool.getResource();
    }

    @Override
    public String getKey(String key) {
        return redisPrefix + key;
    }

    @Override
    public String trimPrefix(String key) {
        if (key.length() < redisPrefix.length()) {
            return null;
        }

        return key.substring(redisPrefix.length());
    }

    @Override
    public <T> T doWithCallback(IRedisClientCallback<T> callback) {
        T result = null;
        try (ShardedJedis jedis = getResource()) {
            if (jedis != null) {
                result = callback.run(jedis);
            }
        } catch (Exception e) {
            Log.err(e.getMessage(), e);
        }
        return result;
    }

    public void setRedisPrefix(String redisPrefix) {
        this.redisPrefix = redisPrefix;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }
}
