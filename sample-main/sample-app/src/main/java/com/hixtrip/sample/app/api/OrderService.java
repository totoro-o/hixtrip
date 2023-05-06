package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.client.PayCallbackReq;
import com.hixtrip.sample.domain.order.model.Order;

public interface OrderService {

    Order create(OrderReq orderReq, Long userId);

    void handleCallback(PayCallbackReq payCallbackReq);
}
