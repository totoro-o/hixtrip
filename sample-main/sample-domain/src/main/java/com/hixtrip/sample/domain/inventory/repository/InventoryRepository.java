package com.hixtrip.sample.domain.inventory.repository;

public interface InventoryRepository {
    Long getInventory(String skuId);

    /**
     * 更新占用库存
     *
     * @param skuId            商品skuId
     * @param occupiedQuantity 占用库存
     */
    Boolean updateOccupiedQuantity(String skuId, Long occupiedQuantity);
}
