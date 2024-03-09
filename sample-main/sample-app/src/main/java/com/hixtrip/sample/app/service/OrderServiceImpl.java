package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.factory.PayCallbackHandlerFactory;
import com.hixtrip.sample.domain.pay.handler.PayCallbackHandler;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PayCallbackHandlerFactory payCallbackHandlerFactory;

    @Override
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 处理请求，DTO转化为Order实体
        Order order = OrderConvertor.INSTANCE.orderDtoTOOrder(commandOderCreateDTO);
        // 创建订单
        orderDomainService.createOrder(order);
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = OrderConvertor.INSTANCE.payDtoToPay(commandPayDTO);
        PayCallbackHandler handler = payCallbackHandlerFactory.getHandler(commandPay.getPayStatus());
        handler.handlerCallback(commandPay);
    }
}
