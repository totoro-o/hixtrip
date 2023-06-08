package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.convertor.InventoryDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 库存mapper映射
 */
@Mapper
public interface InventoryMapper {
    /**
     * 通过skuId查找sku信息
     * @param skuId
     * @return
     */
    InventoryDO findBySkuId(String skuId);

    /**
     * 修改库存
     * @param inventoryDO
     */
    int updateInventory(InventoryDO inventoryDO);
}
