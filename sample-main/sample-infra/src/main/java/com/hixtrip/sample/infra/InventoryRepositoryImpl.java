package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.repository.InventoryDORepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private static final String INVENTORY_KEY_PREFIX = InventoryDO.class.getAnnotation(RedisHash.class).value() + ":";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private InventoryDORepository inventoryDORepository;

    @Autowired
    private RedisScript<Long> inventoryIncScript;


    @Override
    public Optional<Inventory> getInventory(String skuId) {
        return inventoryDORepository.findById(skuId).map(InventoryDOConvertor.INSTANCE::doToDomain);
    }

    @Override
    public Long incrementInventory(String skuId, int sellableQuantityInc, int withholdingQuantityInc, int occupiedQuantityInc) {
        return redisTemplate.execute(inventoryIncScript, List.of(INVENTORY_KEY_PREFIX + skuId), sellableQuantityInc, withholdingQuantityInc, occupiedQuantityInc);
    }

}
