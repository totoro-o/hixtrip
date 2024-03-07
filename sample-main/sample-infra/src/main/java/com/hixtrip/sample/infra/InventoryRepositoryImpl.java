package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class  InventoryRepositoryImpl implements InventoryRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private Map<String, Object> inventoryMap;

    public void init(Map<String, Object> init){
        this.inventoryMap = init;
    }

    @Override
    public Inventory getInventoryBySkuId(String skuId) {
        return (Inventory)this.inventoryMap.get(skuId);
    }

    @Override
    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        Object o = this.inventoryMap.get(skuId);
        if (o == null){
            return false;
        }else {
            Inventory inventory = (Inventory)o;
            inventory.setSellableQuantity(sellableQuantity);
            inventory.setWithholdingQuantity(withholdingQuantity);
            inventory.setOccupiedQuantity(occupiedQuantity);
            this.inventoryMap.put(skuId,inventory);
            return true;
        }
    }
}
