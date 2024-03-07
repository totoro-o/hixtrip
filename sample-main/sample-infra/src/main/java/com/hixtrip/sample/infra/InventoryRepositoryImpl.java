package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final static String INVENTORY_KEY = "inventory";
    @Override
    public void init() {
        Inventory inventory = Inventory.builder()
                .skuId("skuId123")
                .sellableQuantity(2l)
                .withholdingQuantity(0l)
                .occupiedQuantity(0l)
                .build();
        redisTemplate.opsForHash().put(INVENTORY_KEY, "skuId123", inventory);
    }

    @Override
    public Optional<Inventory> getInventory(String skuId) {

        if (Boolean.FALSE.equals(redisTemplate.hasKey(INVENTORY_KEY))) {
            return Optional.empty();
        }

        Inventory inventory = (Inventory) redisTemplate.opsForHash().get(INVENTORY_KEY, skuId);
        return Optional.ofNullable(inventory);
    }

    @Override
    public Boolean updateInventory(Inventory inventory) {
        // TODO: 缺失判定是否更新成功，默认均成功
        redisTemplate.opsForHash().put(INVENTORY_KEY, inventory.getSkuId(), inventory);
        return true;
    }
}
