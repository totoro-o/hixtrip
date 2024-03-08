package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.model.Order;

/**
 * 订单的service层
 */
public interface OrderService {


    Order order(CommandOderCreateDTO commandOderCreateDTO, String loginAccount);

    String payCallback(CommandPayDTO commandPayDTO);
}
