package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.sample.vo.OrderVO;
import com.hixtrip.sample.client.sample.vo.PayVO;
import com.hixtrip.sample.domain.order.model.Order;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param commandOderCreateDTO
     * @return
     */
    OrderVO createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * 支付结果的回调通知
     * @param commandPayDTO 支付结果
     * @return
     */
    PayVO payCallback(CommandPayDTO commandPayDTO);

}
