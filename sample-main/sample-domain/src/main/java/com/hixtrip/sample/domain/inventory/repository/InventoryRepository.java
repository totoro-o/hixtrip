package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {


    /**
     * 获取sku当前库存
     * @param skuId
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     * @return
     */
    Integer getInventory(String skuId, String type);

    /**
     * 修改库存
     * @param skuId
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

    /**
     * 修改库存 同步
     * @param skuId
     * @param quantity 库存
     * @param type 库存类型 sellable可售，withholding预占，occupied占用
     * @return 剩余库存
     */
    Integer changeInventory(String skuId, Integer quantity, String type);
}
