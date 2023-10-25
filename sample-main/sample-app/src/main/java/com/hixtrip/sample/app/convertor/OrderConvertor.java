package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.command.CreateOrderCommand;
import com.hixtrip.sample.domain.pay.command.PayCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper
public interface OrderConvertor {

    OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);


    CreateOrderCommand toCommand(CommandOderCreateDTO commandOderCreateDTO);

    PayCommand toCommand(CommandPayDTO commandOderCreateDTO);

}
