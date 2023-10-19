package com.hixtrip.sample.domain.common;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * <p> 一个虚拟的Redis分布式锁
 *
 * @author airness
 * @since 2023/10/18 16:06
 */
public interface SimpleRedisLock {

    /**
     * 锁定资源，成功锁定时调用runnable内容
     * @param key 锁定资源
     * @param expireAt 锁过期时间
     * @param runnable 调用逻辑
     * @param orFail 若锁定失败逻辑
     */
    void lock(String key, Duration expireAt, Runnable runnable, Runnable orFail);

    /**
     * 锁定资源，成功锁定时调用指定逻辑并返回内容
     * @param key 锁定资源
     * @param expireAt 锁过期时间
     * @param supplier 调用逻辑
     * @param orFail 若锁定失败逻辑
     */
    <T> T lock(String key, Duration expireAt, Supplier<T> supplier, Runnable orFail);
}
