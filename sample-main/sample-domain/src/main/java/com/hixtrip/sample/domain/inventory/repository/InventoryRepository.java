package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Sku;

/**
 *
 */
public interface InventoryRepository {
    Integer getInventory(String skuId);

    void seInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

    Sku getSkuInfo(String skuId);
}
