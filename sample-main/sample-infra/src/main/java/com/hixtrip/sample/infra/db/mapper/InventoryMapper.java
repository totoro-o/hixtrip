package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.InventoryDO;

public interface InventoryMapper {

    InventoryDO findBySkuId(String skuId);

    int updateQuantity(InventoryDO inventoryDO);
}
