package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String SKU_INVENTORY_KEY = "sku_inventory";

    @Override
    public Inventory getInventory(String skuId) {
        Object v = redisTemplate.opsForHash().get(SKU_INVENTORY_KEY, skuId);

        return v == null ? null : (Inventory) v;
    }

    @Override
    public Boolean changeInventory(Inventory inventory) {
        return redisTemplate.opsForHash().putIfAbsent(SKU_INVENTORY_KEY, inventory.getSkuId(),inventory);
    }
}
