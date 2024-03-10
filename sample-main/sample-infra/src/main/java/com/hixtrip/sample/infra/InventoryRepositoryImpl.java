package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.conf.RedisConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据skuId获得库存情况
     *
     * @param skuId
     * @return
     */
    @Override
    public Inventory getInventory(String skuId) {
        String key = RedisConf.INVENTORY_KEY + skuId;
        if (redisTemplate.hasKey(key)) {
            Integer sellableQuantity = (Integer) redisTemplate.opsForHash().get(key, RedisConf.INVENTORY_SELLABLE_QUANTITY);
            Integer withholdingQuantity = (Integer) redisTemplate.opsForHash().get(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY);
            Integer occupiedQuantity = (Integer) redisTemplate.opsForHash().get(key, RedisConf.INVENTORY_OCCUPIED_QUANTITY);
            sellableQuantity = sellableQuantity == null ? Integer.valueOf(0) : sellableQuantity;
            withholdingQuantity = withholdingQuantity == null ? Integer.valueOf(0) : withholdingQuantity;
            occupiedQuantity = occupiedQuantity == null ? Integer.valueOf(0) : occupiedQuantity;
            return new Inventory(skuId, sellableQuantity.longValue(), withholdingQuantity.longValue(), occupiedQuantity.longValue());
        }
        return null;
    }

    /**
     * 预占库存
     *
     * @param skuId
     * @param amount
     * @return
     */
    @Override
    public Boolean withHolding(String skuId, Integer amount) {
        String key = RedisConf.INVENTORY_KEY + skuId;
        if (redisTemplate.hasKey(key) && (Integer) redisTemplate.opsForHash().get(key, RedisConf.INVENTORY_SELLABLE_QUANTITY) >= amount) {
            if (redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_SELLABLE_QUANTITY, -amount) >= 0) {
                redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY, amount);
                return true;
            } else {
                //存在超卖，预占库存失败
                redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_SELLABLE_QUANTITY, amount);
            }
        }
        return false;
    }

    /**
     * 占用库存
     *
     * @param skuId
     * @param amount
     * @return
     */
    @Override
    public Boolean occupied(String skuId, Integer amount) {
        String key = RedisConf.INVENTORY_KEY + skuId;
        if (redisTemplate.hasKey(key) && (Integer) redisTemplate.opsForHash().get(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY) >= amount) {
            if (redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY, -amount) >= 0) {
                redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_OCCUPIED_QUANTITY, amount);
                return true;
            } else {
                //占用异常
                redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY, amount);
//                throw new BusinessException("异常");
            }
        }
        return false;
    }

    /**
     * 释放预占库存
     * @param skuId
     * @param amount
     * @return
     */
    @Override
    public Boolean releaseHolding(String skuId, Integer amount) {
        String key = RedisConf.INVENTORY_KEY + skuId;
        if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_WITHHOLDING_QUANTITY, -amount);
            redisTemplate.opsForHash().increment(key, RedisConf.INVENTORY_SELLABLE_QUANTITY, amount);
            return true;
        }
        return false;
    }
}
