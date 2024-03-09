package com.hixtrip.sample.domain.inventory.repository;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 获取可用库存
     * @param skuId
     * @return
     */
    Integer getInventory(String skuId);

    /**
     * 修改库存
     *
     * @param skuId
     * @param
     * @param withholdingQuantity 预占库存
     * @param
     * @return
     */
    Boolean changeInventory(String skuId,  Long withholdingQuantity);

}
