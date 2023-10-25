package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.command.PayCommand;
import com.hixtrip.sample.domain.pay.enmus.PayStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * DTO像 -> 领域对象转换器
 * 转换器
 */
@Mapper(imports = {PayStatusEnum.class})
public interface PayConvertor {

    PayConvertor INSTANCE = Mappers.getMapper(PayConvertor.class);

    @Mapping(target = "payStatus",  expression = "java(PayStatusEnum.getByCode(commandOderCreateDTO.getPayStatus()))")
    PayCommand toCommand(CommandPayDTO commandOderCreateDTO);

}
