package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    Inventory getInventory(String skuId);

    Boolean changeInventory(Inventory inventory);
}
