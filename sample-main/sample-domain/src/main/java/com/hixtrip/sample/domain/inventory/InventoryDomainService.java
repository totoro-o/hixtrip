package com.hixtrip.sample.domain.inventory;

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
        if (inventory == null) {
            return null;
        }
        return inventory;
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
    public Boolean changeInventory(Inventory inventory) {
        //todo 需要你在infra实现，只需要实现缓存操作。
        if (inventory.getWithholdingFlag()) {
            return inventoryRepository.changeWithholdingQuantity(inventory.getSkuId(), inventory.getWithholdingNum());
        }
        if (inventory.getOccupiedFlag()) {
            return inventoryRepository.changeOccupiedQuantity(inventory.getSkuId(), inventory.getOccupiedNum());
        }
        return false;
    }
}
