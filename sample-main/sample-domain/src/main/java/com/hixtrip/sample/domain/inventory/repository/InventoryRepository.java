package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

/**
 *
 */
public interface InventoryRepository {

    /**
     * 根据skuId获得库存情况
     * @param skuId
     * @return
     */
    Inventory getInventory(String skuId);

    /**
     * 预占库存
     * @param skuId
     * @param amount
     * @return
     */
    Boolean withHolding(String skuId, Integer amount);

    /**
     * 占用库存
     * @param skuId
     * @param amount
     * @return
     */
    Boolean occupied(String skuId, Integer amount);

    /**
     * 释放预占库存
     * @param skuId
     * @param amount
     * @return
     */
    Boolean releaseHolding(String skuId, Integer amount);
}
