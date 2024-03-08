package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderCreateVO;
import com.hixtrip.sample.client.order.vo.PayVO;

/**
 * 订单的service层
 */
public interface OrderService {


    /**
     * 创建订单
     * @param commandOderCreateDTO
     * @return
     */
    OrderCreateVO createOrder(CommandOderCreateDTO commandOderCreateDTO);

    /**
     * 订单回调通知
     * @param commandPayDTO
     * @return
     */
    PayVO payCallBack(CommandPayDTO commandPayDTO);
}
