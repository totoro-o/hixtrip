package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface OrderService {


    /**
     * 创建订单
     *
     * @param commandOderCreateDTO 入参对象
     * @return 订单id
     */
    String createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * 支付回调
     *
     * @param commandPayDTO 入参对象
     */
    void payCallback(CommandPayDTO commandPayDTO);
}
