package com.hixtrip.sample.infra.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisStock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean acquireLock(String lockKey) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", 100, TimeUnit.MILLISECONDS);
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }

}
