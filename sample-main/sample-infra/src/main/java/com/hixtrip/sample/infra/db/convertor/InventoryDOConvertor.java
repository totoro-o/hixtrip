package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper
public interface InventoryDOConvertor {
    InventoryDOConvertor INSTANCE = Mappers.getMapper(InventoryDOConvertor.class);

    Inventory doToDomain(InventoryDO inventoryDO);
}
