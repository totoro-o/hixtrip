package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

public interface InventoryRepository {

    Long getSellableQuantity(String skuId);

    int updateInventory(Inventory inventory);
}
