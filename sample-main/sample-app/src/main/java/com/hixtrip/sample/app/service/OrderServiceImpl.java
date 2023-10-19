package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.CommandPayConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderPaymentVO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDomainService orderDomainService;
    private final PayDomainService payDomainService;

    @Override
    public OrderPaymentVO createOrder(CommandOderCreateDTO form) {
        return Optional.of(OrderConvertor.INSTANCE.toOrder(form))
                .map(orderDomainService::createOrder)
                .map(OrderConvertor.INSTANCE::toOrderPaymentVO)
                .orElse(null);
    }

    @Override
    public void commandPayCallback(CommandPayDTO form) {
        CommandPay dto = CommandPayConvertor.INSTANCE.toDomainObject(form);
        payDomainService.payRecord(dto);
    }
}
