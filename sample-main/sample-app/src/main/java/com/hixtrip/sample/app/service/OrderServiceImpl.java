package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
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
    private PayDomainService payDomainService;

    @Override
    public String createOrder(CommandOderCreateDTO commandOderCreateDTO) {

        Order order = OrderConvertor.INSTANCE.commandOderCreateDTOToOrder(commandOderCreateDTO);

        orderDomainService.createOrder(order);

        // 模拟 返回统一结果集
        return "success";
    }

    @Override
    public String orderPay(CommandPayDTO commandPayDTO) {


        CommandPay commandPay = OrderConvertor.INSTANCE.commandPayDTOToCommandPay(commandPayDTO);

        return payDomainService.payRecord(commandPay);
        // 模拟 返回统一结果集
    }
}
