package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Slf4j
@Component
public class InventoryRepositoryImpl implements InventoryRepository, InitializingBean {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String INVENTORY_REDIS_KEY = "INVENTORY::";
    private static final String SELLABLE_REDIS_KEY = "SELLABLE";
    private static final String WITHHOLDING_REDIS_KEY = "WITHHOLDING";
    private static final String OCCUPIED_REDIS_KEY = "OCCUPIED";

    @Override
    public void afterPropertiesSet() throws Exception {
        //缓存KEY
        String key = INVENTORY_REDIS_KEY + "TEST";
        redisTemplate.opsForHash().put(key, SELLABLE_REDIS_KEY, 100L);
        redisTemplate.opsForHash().put(key, WITHHOLDING_REDIS_KEY, 0L);
        redisTemplate.opsForHash().put(key, OCCUPIED_REDIS_KEY, 0L);
    }


    @Override
    public Inventory getInventory(String skuId) {
        //缓存KEY
        String key = INVENTORY_REDIS_KEY + skuId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            //获取库存
            Long sellable = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(key, SELLABLE_REDIS_KEY)));
            Long withholding = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(key, WITHHOLDING_REDIS_KEY)));
            Long occupied = Long.parseLong(String.valueOf(redisTemplate.opsForHash().get(key, OCCUPIED_REDIS_KEY)));
            return new Inventory(skuId, sellable, withholding, occupied, 0L, 0L);
        }
        return null;
    }

    @Override
    public Boolean changeWithholdingQuantity(String skuId, long num) {
        //缓存KEY
        String key = INVENTORY_REDIS_KEY + skuId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            //获取库存
            Inventory curr = getInventory(skuId);
            //库存不足
            if (curr.getSellableQuantity() < num) {
                return false;
            }
            //redis原子操作 保证线程安全
            Long sellableAfter = redisTemplate.opsForHash().increment(key, SELLABLE_REDIS_KEY, -num);
            if (sellableAfter >= 0) {
                redisTemplate.opsForHash().increment(key, WITHHOLDING_REDIS_KEY, num);
                return true;
            } else {
                //库存不足 回滚库存
                redisTemplate.opsForHash().increment(key, SELLABLE_REDIS_KEY, num);
                return false;
            }
        }
        return false;
    }

    @Override
    public Boolean changeOccupiedQuantity(String skuId, long num) {
        //缓存KEY
        String key = INVENTORY_REDIS_KEY + skuId;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            //获取库存
            Inventory curr = getInventory(skuId);
            //库存不足
            if (curr.getWithholdingQuantity() < num) {
                return false;
            }
            //redis原子操作 保证线程安全
            Long withholdingAfter = redisTemplate.opsForHash().increment(key, WITHHOLDING_REDIS_KEY, -num);
            if (withholdingAfter >= 0) {
                redisTemplate.opsForHash().increment(key, OCCUPIED_REDIS_KEY, num);
                return true;
            } else {
                redisTemplate.opsForHash().increment(key, WITHHOLDING_REDIS_KEY, num);
                return false;
            }

        }
        return false;
    }


}
