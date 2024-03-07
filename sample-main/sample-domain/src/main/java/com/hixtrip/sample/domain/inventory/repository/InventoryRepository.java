package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 查询库存
     *
     * @param skuId skuId
     * @return 库存
     */
    Inventory getInventory(String skuId);

    /**
     * 修改预占库存
     * @param skuId
     * @param num
     * @return
     */
    Boolean changeWithholdingQuantity(String skuId,long num);

    /**
     * 修改占用
     * @param skuId
     * @param num
     * @return
     */
    Boolean changeOccupiedQuantity(String skuId,long num);

}
