package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {
    /**
     * 获取sku当前库存
     * @param skuId skuId
     * @return 数量
     */
    Integer getInventory(String skuId);

    /**
     * 修改库存
     * @param skuId skuId
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
    Boolean changeInventory(String skuId, Long withholdingQuantity,Long occupiedQuantity);

}
