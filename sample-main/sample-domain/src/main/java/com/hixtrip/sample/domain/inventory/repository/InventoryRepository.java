package com.hixtrip.sample.domain.inventory.repository;


import com.hixtrip.sample.domain.inventory.dto.InventoryDto;

/**
 * 领域层定义接口
 */
public interface InventoryRepository {


    /**
     * 获取sku当前库存
     * @param skuId
     * @return
     */
    Integer getInventory(String skuId);


    /**
     * 修改库存
     * @return
     */
    Boolean changeInventory(InventoryDto inventoryDto);
}
