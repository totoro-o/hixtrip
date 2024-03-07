package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    /**
     * orderDO 转 Order
     * @param orderDO 订单持久化对象
     * @return
     */
    Order doToDomain(OrderDO orderDO);

    /**
     * Order 转 orderDO
     * @param order 订单
     * @return
     */
    OrderDO domainToDo(Order order);
}
