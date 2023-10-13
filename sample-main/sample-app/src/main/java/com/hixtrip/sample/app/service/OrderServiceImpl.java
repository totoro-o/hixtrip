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
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PayDomainService payDomainService;

    /**
     * 创建订单
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 处理Request，DTO转换成Order实体
        Order order = OrderConvertor.INSTANCE.OrderDtoToOrder(commandOderCreateDTO);
        // 创建订单
        orderDomainService.createOrder(order);
    }

    /**
     * 支付回调
     * @param commandPayDTO
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = OrderConvertor.INSTANCE.PayDtoToPay(commandPayDTO);
        // 根据状态回调
        payDomainService.payRecord(commandPay);
    }

}
