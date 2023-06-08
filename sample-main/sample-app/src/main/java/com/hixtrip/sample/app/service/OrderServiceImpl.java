package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.config.PayCallbackStrategy;
import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private PayCallbackStrategy payCallbackStrategy;

    @Override
    public void createOrder(Order order) {
         orderDomainService.createOrder(order);
    }

    @Override
    public String handPayCallback(PayCallbackRequest request) {

     return  payCallbackStrategy.handlePayCallback(request);


    }




}
