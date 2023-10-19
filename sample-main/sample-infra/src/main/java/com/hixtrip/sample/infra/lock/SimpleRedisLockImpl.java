package com.hixtrip.sample.infra.lock;

import com.hixtrip.sample.domain.common.SimpleRedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <p> 分布式锁简单实现逻辑
 *
 * @author airness
 * @since 2023/10/18 16:09
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleRedisLockImpl implements SimpleRedisLock {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<Integer> unlockScript = new DefaultRedisScript<>("""
            if redis.call('get', KEYS[1]) == ARGV[1] then
                redis.call('del', KEYS[1])
                return 1
            end
            return 0
            """);


    private <T> T lockAndReturn(String key, Duration expireAt, Supplier<T> supplier, Runnable orFail) {
        String uid = UUID.randomUUID().toString();
        Boolean res;
        int round = 10;
        do {
            res = redisTemplate.opsForValue().setIfAbsent(key, uid, expireAt.getSeconds(), TimeUnit.SECONDS);
        } while (Boolean.FALSE.equals(res) || --round >= 0);
        if (Boolean.TRUE.equals(res)) {
            try {
                return supplier.get();
            } finally {
                // 解锁资源
                boolean unlockRes = unlock(key, uid);
                if (!unlockRes) {
                    // 记录日志，此处解锁失败可能是业务逻辑执行时间超过了锁过期时间导致失败，这里只实现简单分布式锁，实际可使用redisson的watch dog机制
                    log.warn("记录锁失败, key={}, uid={}, expire={}s", key, uid, expireAt.getSeconds());
                }
            }
        } else {
            orFail.run();
        }
        return null;
    }

    @Override
    public void lock(String key, Duration expireAt, Runnable runnable, Runnable orFail) {
        lockAndReturn(key, expireAt, () -> {
            runnable.run();
            return null;
        }, orFail);
    }

    @Override
    public <T> T lock(String key, Duration expireAt, Supplier<T> supplier, Runnable orFail) {
        return lockAndReturn(key, expireAt, supplier, orFail);
    }


    /**
     * 解锁资源
     * @param key 被锁定资源
     * @param uuid 锁定后ID
     */
    private boolean unlock(String key, String uuid) {
        Integer res = redisTemplate.execute(unlockScript, Collections.singletonList(key), uuid);
        return Objects.nonNull(res) && res == 1;
    }
}
