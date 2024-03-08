package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;

/**
 *
 */
public interface OrderRepository {
    /**
     * 创建订单
     * @param order
     */
    void createOrder(Order order);

    /**
     * 根据id获取订单
     * @param orderId
     * @return
     */
    Order getOrderByOrderId(String orderId);

    /**
     * 修改订单状态-成功
     * @param orderId
     * @param payId
     * @param orderStatus
     * @param payStatus
     * @return
     */
    boolean orderToSuccess(String orderId,String payId, String orderStatus, String payStatus);

    /**
     * 判断订单是否支付
     * @param orderId
     * @param payStatus
     * @return
     */
    boolean isNoPay(String orderId, String payStatus);

}
