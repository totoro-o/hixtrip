package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryDOConvertor {
    InventoryDOConvertor INSTANCE = Mappers.getMapper(InventoryDOConvertor.class);

    /**
     * 把领域层对象转化为基础层对象
     * @param inventory
     * @return
     */
    InventoryDO toInventoryDO(Inventory inventory);

    /**
     * 把基础层对象转化为领域层对象
     * @param inventoryDO
     * @return
     */
    Inventory toInventory(InventoryDO inventoryDO);
}
