package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.conf.InventoryConf;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    public Inventory getInventory(String skuId) {
        //todo 需要你在infra实现，只需要实现缓存操作, 返回的领域对象自行定义
        Inventory inventory = inventoryRepository.getInventory(skuId);
        return inventory;
    }

    /**
     * 修改库存
     * @param inventory
     * @param operationType 操作类型
     * @param amount    修改库存数量
     * @return
     */
    public Boolean changeInventory(Inventory inventory, String operationType, Integer amount) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        if (InventoryConf.WITHHOLDING.equals(operationType)) {
            return inventoryRepository.withHolding(inventory.getSkuId(), amount);
        } else if (InventoryConf.OCCUPIED.equals(operationType)) {
            return inventoryRepository.occupied(inventory.getSkuId(), amount);
        } else if (InventoryConf.RELEASE_HOLDING.equals(operationType)) {
            inventoryRepository.releaseHolding(inventory.getSkuId(), amount);
        }
        return false;
    }
}
