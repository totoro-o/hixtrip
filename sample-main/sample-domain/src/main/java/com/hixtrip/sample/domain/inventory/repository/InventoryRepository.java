package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {


    Integer getInventory(String skuId);

    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
