package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.domain.inventory.model.Inventory;

public interface InventoryMapper {

    Inventory getBySkuId(String skuId);

    /**
     * 下订单扣减可售库存
     * @param sellableQuantity
     * @param withholdingQuantity
     * @param occupiedQuantity
     * @return
     */
    Integer changeInventory(String skuid, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);

    Integer changeIntentoryOnPaySuccess(String skuId, Long withholdingQuantity);

    Integer changeIntentoryOnPayFail(String skuId, Long withholdingQuantity);
}
