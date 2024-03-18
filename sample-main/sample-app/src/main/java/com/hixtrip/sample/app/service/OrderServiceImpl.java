package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.vo.OrderVo;
import com.hixtrip.sample.client.user.dto.UserDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderDomainService orderDomainService;



    @Override
    public OrderVo createOrder(UserDTO userDTO, CommandOderCreateDTO commandOderCreateDTO) {
        Order order = Order.builder()
                .userId(userDTO.getId())
                .skuId(commandOderCreateDTO.getSkuId())
                .amount(commandOderCreateDTO.getAmount())
                .payTime(LocalDateTime.now())
                .payStatus("待付款")
                .createBy(userDTO.getName())
                .createTime(LocalDateTime.now())
                .updateBy(userDTO.getName())
                .updateTime(LocalDateTime.now())
                .delFlag(0L)
                .build();
        Order orderData = orderDomainService.createOrder(order);
        return OrderConvertor.INSTANCE.orderToOrderVO(orderData);
    }

    @Override
    public void orderPaySuccess(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }

    @Override
    public void orderPayFail(CommandPay commandPay) {
        orderDomainService.orderPayFail(commandPay);
    }
}
