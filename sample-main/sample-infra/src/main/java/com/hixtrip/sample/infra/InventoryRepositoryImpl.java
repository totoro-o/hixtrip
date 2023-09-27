package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public Integer getInventory(String skuId) {
        Object inventory = redisTemplate.opsForValue().get("INVENTORY:" + skuId);
        return inventory == null ? 0 : Integer.parseInt(inventory.toString());
    }

    @Override
    public Boolean changeInventory(String skuId, Long amount) {

        String lockKey = "INVENTORY:LOCK:" + skuId;

        // 100并发考虑使用分布式锁
        Boolean lockAcquired = redisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);

        if (lockAcquired == null || !lockAcquired) {
            return false;
        }

        try {
            // 执行库存扣减操作
            Long result = redisTemplate.opsForValue().decrement("INVENTORY:" + skuId, amount);
            return result != null && result >= 0;
        } finally {
            // 释放锁
            redisTemplate.delete(lockKey);
        }

    }
}
