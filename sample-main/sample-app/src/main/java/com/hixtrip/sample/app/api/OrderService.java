package com.hixtrip.sample.app.api;

import com.hixtrip.sample.domain.order.command.CreateOrderCommand;
import com.hixtrip.sample.domain.order.model.Order;

/**
 * 订单的service层
 */
public interface OrderService {


    Order createOrder(CreateOrderCommand command);
}
