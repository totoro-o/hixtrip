package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    Order doToDomain(OrderDO orderDO);

    OrderDO domainToDo(Order order);
}
