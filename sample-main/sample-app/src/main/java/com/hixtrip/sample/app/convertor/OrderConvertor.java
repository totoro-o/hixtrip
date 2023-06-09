package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.OrderCreateReq;
import com.hixtrip.sample.client.order.OrderCreateVO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderCreate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderConvertor {

    OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

    @Mapping(target = "userId", source = "userId")
    OrderCreate reqToDomain(Long userId, OrderCreateReq req);


    OrderCreateVO doMainToVO(Order order);

}

