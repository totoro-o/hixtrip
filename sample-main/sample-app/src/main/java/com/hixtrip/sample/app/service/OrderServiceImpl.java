package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.app.convertor.PayConvertor;
import com.hixtrip.sample.app.strategy.OrderPayCallbackStrategy;
import com.hixtrip.sample.app.strategy.OrderPayCallbackStrategyFactory;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.constants.OrderPayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.CommandOrderCreate;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDomainService orderDomainService;
    private final PayDomainService payDomainService;
    private final OrderPayCallbackStrategyFactory orderPayCallbackStrategyFactory;

    @Override
    public String createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        CommandOrderCreate commandOrderCreate = OrderConvertor.INSTANCE.toDomain(commandOderCreateDTO);
        Order order = orderDomainService.createOrder(commandOrderCreate);
        return order.getId();
    }

    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = PayConvertor.INSTANCE.toCommandPay(commandPayDTO);
        // 记录支付回调结果
        payDomainService.payRecord(commandPay);

        OrderPayStatusEnum payStatus = OrderPayStatusEnum.getByCode(commandPayDTO.getPayStatus());
        Assert.notNull(payStatus, "payStatus不合法");

        // 根据支付状态调用不同的策略
        OrderPayCallbackStrategy strategy = orderPayCallbackStrategyFactory.getStrategy(payStatus.getCode())
                .orElseThrow(() -> new RuntimeException("找不到对应的策略"));
        strategy.handle(commandPay.getOrderId());
    }
}
