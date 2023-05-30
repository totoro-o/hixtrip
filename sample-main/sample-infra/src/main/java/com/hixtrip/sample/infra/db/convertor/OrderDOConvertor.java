package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.inventory.Inventory;
import com.hixtrip.sample.domain.order.Order;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 */
@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    Order doToDomain(OrderDO orderDO);
    OrderDO domainToDo(Order order);


}
