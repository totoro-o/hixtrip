package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.InventoryDO;

public interface InventoryMapper {
    InventoryDO getBySkuId();

    Integer updateOccupiedQuantity(String skuId, Long occupiedQuantity);

}
