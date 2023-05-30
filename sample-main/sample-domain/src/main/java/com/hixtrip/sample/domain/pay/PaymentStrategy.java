package com.hixtrip.sample.domain.pay;

import java.util.function.Predicate;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */

public interface PaymentStrategy extends Predicate<PaymentStrategy.PaymentStatus> {
  void processPayment(Long orderId, String number);


  enum PaymentStatus {
    SUCCESS("支付成功"),
    FAIL("支付失败"),
    DUP("重复支付");

    PaymentStatus(String status) {
    }
  }

}
