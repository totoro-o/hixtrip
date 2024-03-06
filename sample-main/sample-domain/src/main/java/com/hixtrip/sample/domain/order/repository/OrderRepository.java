package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    /**
     * 创建订单
     * @param order 订单参数
     */
    void createOrder(Order order);

    /**
     * 待付款订单支付成功
     */
    void orderPaySuccess(CommandPay commandPay);

    /**
     * 待付款订单支付失败
     */
    void orderPayFail(CommandPay commandPay);
}
