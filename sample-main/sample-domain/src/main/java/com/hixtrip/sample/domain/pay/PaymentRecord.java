package com.hixtrip.sample.domain.pay;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public interface PaymentRecord {
  default void paymentRecord() {
    getService().payRecord();
  }

  PayDomainService getService();
}
