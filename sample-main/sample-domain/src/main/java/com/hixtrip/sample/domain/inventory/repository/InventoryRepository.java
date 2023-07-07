package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.model.Inventory;

public interface InventoryRepository {

    /**
     *
     * @param skuid
     * @return
     */
    Inventory getBySkuId(String skuid);

    /**
     * 修改库存
     * @param skuid
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    Integer changeInventory(String skuid, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

    /**
     * 支付成功预占库存移到占用库存
     * @param skuId
     * @param withholdingQuantity
     * @return
     */
    Integer changeIntentoryOnPaySuccess(String skuId, Long withholdingQuantity);

    /**
     * 支付失败预占库存移到可售库存
     * @param skuId
     * @param withholdingQuantity
     * @return
     */
    Integer changeIntentoryOnPayFail(String skuId, Long withholdingQuantity);
}
