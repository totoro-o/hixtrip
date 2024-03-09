package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

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
    public Boolean changeInventory(String skuId,Long withholdingQuantity) {
        String redisKey = "INVENTORY:" + skuId;
        String lockKey = "INVENTORY:LOCK:" + skuId;
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", Duration.ofSeconds(10));
        if (lock == null || !lock) {
            return Boolean.FALSE;
        }
        try {
            int inventory = (int) redisTemplate.opsForValue().get(redisKey);
            if (inventory >= 0 ) {
                if (withholdingQuantity != null ) {
                    int remainingQuantity = (int) (inventory - withholdingQuantity);
                    if (remainingQuantity < 0) {
                        //超卖
                        return Boolean.FALSE;
                    }
                    // 更新库存
                    redisTemplate.opsForValue().set(redisKey, remainingQuantity);
                }
            } else {
                throw new RuntimeException("库存不足");
            }
        } finally {
            if (lock) {
                redisTemplate.delete(lockKey);
            }
        }
        return Boolean.TRUE;
    }

}
