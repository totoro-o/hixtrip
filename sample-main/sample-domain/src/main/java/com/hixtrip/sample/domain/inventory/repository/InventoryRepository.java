package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

import java.util.Optional;

/**
 *
 */
public interface InventoryRepository {


    Optional<Inventory> getInventory(String skuId);

    /**
     * 增加库存，原子性操作
     *
     * @param skuId                  skuId
     * @param sellableQuantityInc    可售库存增量
     * @param withholdingQuantityInc 预占库存增量
     * @param occupiedQuantityInc    占用库存增量
     * @return 0: 库存不存在
     * -1：库存不足
     * -999：库存增量都为0或有 nil 参数
     * 1：成功
     */
    Long incrementInventory(String skuId, int sellableQuantityInc, int withholdingQuantityInc, int occupiedQuantityInc);
}
