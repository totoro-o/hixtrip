package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.client.order.vo.PayCallbaskVO;

/**
 * 订单的service层
 */
public interface OrderService {


    OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO);

    PayCallbaskVO payCallbask(CommandPayDTO commandPayDTO);
}
