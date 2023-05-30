package com.hixtrip.sample.domain.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
@Component
public abstract class AbstractPaymentStrategy implements PaymentRecord, PaymentStrategy {
  @Autowired
  PayDomainService payDomainService;

  @Override
  public void processPayment(Long orderId, String number) {
    paymentRecord();
  }

  @Override
  public PayDomainService getService() {
    return payDomainService;
  }
}
