package com.hixtrip.sample.infra.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import org.mapstruct.Mapper;

@Mapper
public interface InventorMapper extends BaseMapper<InventoryDO> {

    void createInventor(InventoryDO inventoryDO);

    void updateInventory(InventoryDO inventoryDO);

    InventoryDO query(String skuId);
}
