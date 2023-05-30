package com.hixtrip.sample.domain.pay;

import com.hixtrip.sample.domain.order.Order;
import com.hixtrip.sample.domain.order.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
@Component
public class PaymentDuplicateStrategy extends AbstractPaymentStrategy {
  @Autowired
  OrderDomainService orderDomainService;

  @Override
  public void processPayment(Long orderId, String number) {
    super.processPayment(orderId, number);
    orderDomainService.orderPayDup(orderId, number);
  }

  @Override
  public boolean test(PaymentStatus status) {
    return status.equals(PaymentStatus.DUP);
  }
}
