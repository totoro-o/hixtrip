package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对象 -> 领域对象转换器
 */
@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    /**
     * 订单领域对象 -> DO对象
     * @param order
     * @return
     */
    OrderDO convert(Order order);

    /**
     * DO对象 -> 订单领域对象
     * @param orderDO
     * @return
     */
    Order convert(OrderDO orderDO);
}
