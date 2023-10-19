package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.client.order.vo.OrderPaymentVO;

/**
 * 订单的service层
 */
public interface OrderService {


    /**
     * 用户下单
     *
     * @param form 表单
     * @return 返回订单ID，如果返回null，表示下单失败
     */
    OrderPaymentVO createOrder(CommandOderCreateDTO form);


    /**
     * 订单结果回调
     * @param form 回调表单
     */
    void commandPayCallback(CommandPayDTO form);
}
