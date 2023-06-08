package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.PayCallbackRequest;
import com.hixtrip.sample.domain.order.model.Order;

public interface OrderService {
    void createOrder(Order order);

    String handPayCallback(PayCallbackRequest reques);
}
