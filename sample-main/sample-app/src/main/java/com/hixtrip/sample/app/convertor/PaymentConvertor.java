package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.PaymentResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper
public interface PaymentConvertor {

    PaymentConvertor INSTANCE = Mappers.getMapper(PaymentConvertor.class);

    PaymentResult dtoToResult(CommandPayDTO dto);

}
