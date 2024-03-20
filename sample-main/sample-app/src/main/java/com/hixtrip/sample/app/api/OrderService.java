package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderVO;
import com.hixtrip.sample.client.order.vo.PayCallBackVO;


/**
 * 订单的service层
 */
public interface OrderService {

    OrderVO order(CommandOderCreateDTO commandOderCreateDTO);

    PayCallBackVO payCallback(CommandPayDTO commandPayDTO);

}
