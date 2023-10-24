package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public interface OrderRepository {

    /**
     * 创建待付款订单
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
