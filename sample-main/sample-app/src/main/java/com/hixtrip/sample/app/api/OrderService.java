package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreatedVO;

/**
 * 订单的service层
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param commandOderCreateDTO 订单参数
     * @return 创建订单结果
     */

    OrderCreatedVO createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * 支付回调处理
     *
     * @param commandPayDTO
     * @return
     */
    String payCallback(CommandPayDTO commandPayDTO);

}
