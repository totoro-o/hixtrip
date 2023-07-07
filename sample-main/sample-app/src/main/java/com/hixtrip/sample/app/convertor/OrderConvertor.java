package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.app.vo.OrderVo;
import com.hixtrip.sample.domain.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConvertor {
    OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

    Order doToDomain(OrderVo orderVo);
}
