package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.constant.CacheKey;
import com.hixtrip.sample.infra.utils.RedisStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisStock redisStock;
    private String cache_key = CacheKey.cache_stock;
    private String lock_key = CacheKey.lock_stock;

    @Override
    public Inventory getInventory(String skuId) {
        String key = cache_key + skuId;
        if(redisTemplate.hasKey(key)){
            List<Object> objects = redisTemplate.opsForHash().multiGet(key, Arrays.asList("sellableQuantity", "withholdingQuantity", "occupiedQuantity"));
            return new Inventory(skuId,Long.valueOf(objects.get(0).toString()),
                    Long.valueOf(objects.get(1).toString()),Long.valueOf(objects.get(2).toString()));
        }
        return loadInventory(skuId);
    }

    private Inventory loadInventory(String skuId) {
        String cacheKey = cache_key + skuId;
        String lockKey = lock_key + skuId;
        try{
            boolean lock = redisStock.acquireLock(lockKey);
            if(lock){
                Map<String,Long> inventoryMap = new HashMap<>();
                inventoryMap.put("sellableQuantity", 100L);
                inventoryMap.put("withholdingQuantity", 0L);
                inventoryMap.put("occupiedQuantity", 0L);
                redisTemplate.opsForHash().putAll(cacheKey, inventoryMap);
                return new Inventory(skuId,100L, 0L, 0L);
            }
        }finally {
            redisStock.releaseLock(lockKey);
        }
        return null;
    }

    @Override
    public boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        String lockKey = lock_key + skuId;
        try{
            boolean lock = redisStock.acquireLock(lockKey);
            if(lock){
                String cacheKey = cache_key + skuId;
                List<Object> objects = redisTemplate.opsForHash().multiGet(cacheKey, Arrays.asList("sellableQuantity", "withholdingQuantity", "occupiedQuantity"));
                Long sellableQuantityRemain = Long.valueOf(objects.get(0).toString());
                Long withholdingQuantityRemain = Long.valueOf(objects.get(1).toString());
                Long occupiedQuantityRemain = Long.valueOf(objects.get(2).toString());
                if(sellableQuantity!= null && sellableQuantityRemain < sellableQuantity){
                    return false;
                }
                Map<String,Long> inventoryMap = new HashMap<>();
                if(sellableQuantity != null){
                    inventoryMap.put("sellableQuantity", sellableQuantityRemain - sellableQuantity);
                }
                if(withholdingQuantity != null){
                    inventoryMap.put("withholdingQuantity", withholdingQuantityRemain - withholdingQuantity);
                }
                if(occupiedQuantity != null){
                    inventoryMap.put("occupiedQuantity", occupiedQuantityRemain - occupiedQuantity);
                }
                redisTemplate.opsForHash().putAll(cacheKey,inventoryMap);
                return true;
            }
        }finally {
            redisStock.releaseLock(lockKey);
        }
        return false;
    }
}
