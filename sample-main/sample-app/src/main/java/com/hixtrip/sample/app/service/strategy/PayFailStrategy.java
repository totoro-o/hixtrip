package com.hixtrip.sample.app.service.strategy;

import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayFailStrategy implements PayStrategy{
    @Autowired
    private OrderDomainService orderDomainService;
    @Override
    public String pay(CommandPayDTO commandPayDTO) {
        return orderDomainService.orderPayFail(CommandPayConvertor.INSTANCE.sampleToSampleVO(commandPayDTO));
    }
}
