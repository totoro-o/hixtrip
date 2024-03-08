package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.PayService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 订单的service层
 */
@Component
public class PayServiceImpl implements PayService {
    @Autowired
    private PayDomainService payDomainService;
    @Override
    public String Pay(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = new CommandPay();
        BeanUtils.copyProperties(commandPayDTO, commandPay);
        payDomainService.payRecord(commandPay);
        return "success";
    }
}
