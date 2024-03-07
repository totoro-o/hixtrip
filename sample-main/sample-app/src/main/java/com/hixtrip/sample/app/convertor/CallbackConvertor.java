package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.SampleVO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DTO对像 -> 领域对象转换器
 * 转换器
 */
@Mapper
public interface CallbackConvertor {

    CallbackConvertor INSTANCE = Mappers.getMapper(CallbackConvertor.class);

    CommandPay commandPayDTOToCommandPay(CommandPayDTO commandPayDTO);
}
