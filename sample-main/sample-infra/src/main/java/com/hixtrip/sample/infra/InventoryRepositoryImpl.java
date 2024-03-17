package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.constant.InventoryConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Inventory getInventory(String skuId) {
        Long sellableQuantity = (Long) redisTemplate.opsForValue().get(this.getRedisKey(skuId, InventoryConstants.SELLABLE_QUANTITY_KEY));
        Long withholdingQuantity = (Long) redisTemplate.opsForValue().get(this.getRedisKey(skuId, InventoryConstants.WITHHOLDING_QUANTITY_KEY));
        Long occupiedQuantity = (Long) redisTemplate.opsForValue().get(this.getRedisKey(skuId, InventoryConstants.OCCUPIED_QUANTITY_KEY));
        return new Inventory(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
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
        Long result = redisTemplate.execute(
                new DefaultRedisScript<>(InventoryConstants.CHANGE_INVENTORY_SCRIPT, Long.class),
                Arrays.asList(this.getRedisKey(skuId, InventoryConstants.SELLABLE_QUANTITY_KEY),
                        this.getRedisKey(skuId, InventoryConstants.WITHHOLDING_QUANTITY_KEY),
                        this.getRedisKey(skuId, InventoryConstants.OCCUPIED_QUANTITY_KEY)),
                sellableQuantity, withholdingQuantity, occupiedQuantity
        );
        return result != null && result == 0L;
    }

    /**
     * 获取redis key
     *
     * @param skuId    skuId
     * @param valueKey 具体的值key
     * @return
     */
    private String getRedisKey(String skuId, String valueKey) {
        return InventoryConstants.PREFIX_KEY + skuId + InventoryConstants.REDIS_SEPARATOR + valueKey;
    }
}
