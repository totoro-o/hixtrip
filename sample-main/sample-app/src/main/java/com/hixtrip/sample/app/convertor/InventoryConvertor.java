package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.app.vo.OrderVo;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InventoryConvertor {
    InventoryConvertor INSTANCE = Mappers.getMapper(InventoryConvertor.class);

    Inventory doToDomain(OrderVo orderVo);
}
