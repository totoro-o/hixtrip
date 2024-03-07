package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

import java.util.Optional;

/**
 * 库存仓储
 */
public interface InventoryRepository {

    /**
     * 模拟初始化库存
     * 设定 skuId = "skuId123"
     * sellableQuantity = 10
     * withholdingQuantity = 0
     * occupiedQuantity = 0
     */
    void init();

    /**
     * 通过skuId获取库存
     * @param skuId
     * @return 设定 skuId = "skuId123"，才有数值，其余的返回null
     */
    Optional<Inventory> getInventory(String skuId);

    /**
     * 更新库存
     * @param inventory
     * @return
     */
    Boolean updateInventory(Inventory inventory);
}
