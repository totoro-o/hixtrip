package com.hixtrip.sample.domain.inventory;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
public interface InventoryDomainService {
    /**
     * 获取sku当前库存
     *
     * @param skuId
     */
    long getInventory(long skuId);

    /**
     * 修改库存
     *
     * @param skuId
     * @param sellableQuantity    可售库存
     * @param withholdingQuantity 预占库存
     * @param occupiedQuantity    占用库存
     * @return
     */
     Boolean changeInventory(long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);
}
