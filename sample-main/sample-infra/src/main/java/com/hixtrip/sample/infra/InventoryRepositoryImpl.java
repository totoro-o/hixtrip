package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
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
    private static final String LOCK_PREFIX = "lock:";
    private static final Duration DEFAULT_EXPIRE_DURATION = Duration.ofMillis(30000); // 默认过期时间，30秒

    @Override
    public Integer getInventory(String skuId) {
        Inventory inventory = (Inventory) redisTemplate.boundValueOps(skuId).get();
        return Math.toIntExact(inventory != null ? inventory.getSellableQuantity() : 0);
    }

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        // 重试三次
        for (int i = 0; i <3 ; i++) {
            try {
                if (acquireLock(skuId)){
                    Inventory inventory = (Inventory)redisTemplate.boundValueOps(skuId).get();
                    if (null==inventory||inventory.getSellableQuantity()<sellableQuantity){
                        return false;
                    }
                    inventory.setSellableQuantity(inventory.getSellableQuantity()-sellableQuantity);
                    inventory.setWithholdingQuantity(inventory.getWithholdingQuantity()+withholdingQuantity);
                    inventory.setOccupiedQuantity(inventory.getOccupiedQuantity()+occupiedQuantity);
                    return true;
                }else {

                }
            }finally {
                releaseLock(skuId);
            }
        }
        return false;
    }

    public boolean acquireLock(String skuId) {
        String key = LOCK_PREFIX + skuId;
        // 尝试获取锁
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, skuId, DEFAULT_EXPIRE_DURATION);
        return result != null && result;
    }

    public void releaseLock(String skuId) {
        String key = LOCK_PREFIX + skuId;
        // 确保当前线程持有锁，然后释放锁
        if (null!=redisTemplate.opsForValue().get(key)) {
            redisTemplate.delete(key);
        }
    }

}
