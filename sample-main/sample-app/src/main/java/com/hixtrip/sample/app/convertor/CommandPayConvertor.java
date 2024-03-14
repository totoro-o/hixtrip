package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.factory.Mappers;

public interface CommandPayConvertor {

    CommandPayConvertor INSTANCE = Mappers.getMapper(CommandPayConvertor.class);

    CommandPay commandPayCreateTCommandPayDO(CommandPayDTO sample);
}
