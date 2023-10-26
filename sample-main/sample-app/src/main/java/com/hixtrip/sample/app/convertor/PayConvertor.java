package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayConvertor {
    PayConvertor INSTANCE = Mappers.getMapper(PayConvertor.class);

    CommandPay toCommandPay(CommandPayDTO commandPayDTO);
}
