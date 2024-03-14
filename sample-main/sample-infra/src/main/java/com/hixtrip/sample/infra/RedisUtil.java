package com.hixtrip.sample.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    public void set(String key,String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public void set(String key, Object value, long timeout, TimeUnit unit){
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    public <T> T get(String key, Class<?> T){
        return (T) redisTemplate.opsForValue().get(key);
    }

    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Long decr(String key) {
        return  redisTemplate.opsForValue().decrement(key);
    }

    public Long decr(String key ,long delta) {
        return  redisTemplate.opsForValue().decrement(key, delta);
    }

    public Long incr(String key) {
        return  redisTemplate.opsForValue().increment(key);
    }

    public Long incr(String key ,long delta) {
        return  redisTemplate.opsForValue().increment(key, delta);
    }

    public void expire(String key, long time, TimeUnit unit) {
        redisTemplate.expire(key, time, unit);
    }
}
