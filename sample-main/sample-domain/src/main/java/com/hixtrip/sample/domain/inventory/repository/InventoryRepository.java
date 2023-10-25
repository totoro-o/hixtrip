package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

import java.util.Optional;

/**
 *
 */
public interface InventoryRepository {


    Optional<Inventory> getInventory(String skuId);

    /**
     * 库存扣减
     * @param skuId
     * @param diffSq
     * @param diffWq
     * @param diffOq
     * @return
     */
    boolean changeQuantity(String skuId, Long diffSq, Long diffWq, Long diffOq);
}
