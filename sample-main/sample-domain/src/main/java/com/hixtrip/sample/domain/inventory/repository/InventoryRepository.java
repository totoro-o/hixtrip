package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository {
    /**
     * 获取库存
     * @param skuId
     * @return
     */
    Inventory getInventory(String skuId);


    /**
     * 更新库存
     * @param inventory
     */
   void changeInventory(Inventory inventory);

    boolean deductInventory(String skuId, Integer skuNum);

    boolean rollbackInventory(String skuId, Integer skuNum);

}
