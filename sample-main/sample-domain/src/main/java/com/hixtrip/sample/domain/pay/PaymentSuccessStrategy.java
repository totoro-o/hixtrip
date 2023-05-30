package com.hixtrip.sample.domain.pay;

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
public class PaymentSuccessStrategy extends AbstractPaymentStrategy {

  @Autowired
  OrderDomainService orderDomainService;

  @Override
  public void processPayment(Long orderId, String number) {
    super.processPayment(orderId, number);
    orderDomainService.orderPaySuccess(orderId, number);
  }

  @Override
  public boolean test(PaymentStatus status) {
    return status.equals(PaymentStatus.SUCCESS);
  }

}
