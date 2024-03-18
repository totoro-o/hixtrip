package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.vo.OrderVo;
import com.hixtrip.sample.domain.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper
public interface OrderConvertor {

    OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

    OrderVo orderToOrderVO(Order order);

}
