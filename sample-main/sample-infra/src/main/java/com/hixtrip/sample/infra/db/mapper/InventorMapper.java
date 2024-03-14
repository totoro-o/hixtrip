package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.InventoryDO;

public interface InventorMapper {

    void createInventor(InventoryDO inventoryDO);

    void updateInventory(InventoryDO inventoryDO);

    InventoryDO query(String skuId);
}
