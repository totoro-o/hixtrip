package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

  @Autowired
  OrderRepository orderRepository;


  public Order getOrder(Long id) {
    Order order = orderRepository.get(id);
    return order;
  }

  /**
   * 创建待付款订单
   */
  public Order createOrder(BigDecimal skuPrice, Long skuId, Long userId, Long qty) {
    Order order = new Order(userId, skuId, skuPrice, qty);
    return orderRepository.save(order);
  }

  /**
   * 待付款订单支付成功
   */
  public void orderPaySuccess(Long orderId, String number) {
    Order order = orderRepository.get(orderId);
    order.paidSuccess(number);
    orderRepository.save(order);
  }

  /**
   * 待付款订单支付失败
   */
  public void orderPayFail(Long orderId, String number) {
    Order order = orderRepository.get(orderId);
    order.paidFail(number);
    orderRepository.save(order);
  }

  public void orderPayDup(Long orderId, String number) {
    Order order = orderRepository.get(orderId);
    if (order.isPaidSuccess()) {
      // 通知退款聚合根，发起退款
      // ....
    } else if (order.isPaidFail()) {
      order.paidSuccess(number);
    }
    orderRepository.save(order);
  }

}
