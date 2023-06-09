package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDOConvertor {

    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    Order doToDomain(OrderDO orderDO);

    OrderDO domainToDO(Order order);
}
