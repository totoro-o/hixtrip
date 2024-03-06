package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取sku当前库存
     * @param skuId skuId
     * @return 数量
     */
    Integer getInventory(String skuId){
        String redisKey=skuId;
        Object object = redisTemplate.opsForValue().get(redisKey);
        if(!Objects.isNull(object)){
            return Integer.valueOf(object.toString());
        }
        return 0;
    };

    /**
     * 修改库存
     * @param skuId skuId
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return boolean
     */
    public Boolean changeInventory(String skuId, Long withholdingQuantity, Long occupiedQuantity) {
        String redisKey = skuId;

        // 使用分布式锁，避免并发操作导致数据不一致
        String lockKey = "lock:" + skuId;
        Boolean locked = false;
        try {
            locked = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", Duration.ofSeconds(10));
            if (!locked) {
                // 获取锁失败，可能有其他线程在处理相同的操作
                return false;
            }

            // 从redis中获取库存数
            Integer inventory = Optional.ofNullable(redisTemplate.opsForValue().get(redisKey))
                    .map(obj -> Integer.parseInt(obj.toString()))
                    .orElse(0);

            if (withholdingQuantity != null) {
                // 库存为0直接返回
                if (inventory == 0) {
                    return false;
                }

                // 计算剩余库存数
                int remainingInventory = inventory - Math.toIntExact(withholdingQuantity);

                // 判断库存是否为负数
                if (remainingInventory < 0) {
                    return false;
                }

                // 更新库存信息
                redisTemplate.opsForValue().set(redisKey, remainingInventory);

            }

            if (occupiedQuantity != null) {
                inventory += Math.toIntExact(occupiedQuantity);
                // 更新库存信息
                redisTemplate.opsForValue().set(redisKey, inventory);
            }
            return true;

        } finally {
            // 释放锁
            if (locked) {
                redisTemplate.delete(lockKey);
            }
        }
    }



}
