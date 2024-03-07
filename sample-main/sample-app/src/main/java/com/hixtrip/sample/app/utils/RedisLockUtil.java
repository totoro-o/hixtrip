package com.hixtrip.sample.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 15:07
 * redis 锁
 */
@Component
public class RedisLockUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Long LOCK_REDIS_TIMEOUT = 10L;
    private static final Long LOCK_REDIS_WAIT = 500L;

    /**
     * 获取锁
     * @param lockKey
     * @param lockValue
     * @return
     */
    public Boolean getLock(String lockKey, String lockValue) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, Duration.ofSeconds(LOCK_REDIS_TIMEOUT));
        return locked;
    }

    /**
     * 释放锁
     * @param lockKey
     * @param lockValue
     * @return
     */
    public Long removeLock(String lockKey, String lockValue) {
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
        Long releaseStatus = (Long) this.redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
        return releaseStatus;
    }
}
