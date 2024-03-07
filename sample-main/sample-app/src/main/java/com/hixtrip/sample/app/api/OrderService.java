package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 保存订单
     * @param cmd
     */
    void saveOrder(CommandOderCreateDTO cmd);

    /**
     * 支付支付回调
     * @param cmd
     */
    void payCallback(CommandPayDTO cmd);
}
