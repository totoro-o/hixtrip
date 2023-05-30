package com.hixtrip.sample.domain.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/29
 * @since
 */
@Data
public class Order {
  private Long id;
  private Long version;
  private Long creatorId;
  private Instant createdTime;
  private Long modifierId;
  private Instant modifiedTime;
  private String number;
  private Long buyerId;
  private Long sellerId;
  private Long skuId;
  private BigDecimal skuPrice;
  private Long skuQty;
  private BigDecimal amount;
  private PaymentInfo paymentInfo;


  protected Order(Long buyerId, Long skuId, BigDecimal skuPrice, Long skuQty) {
    this.buyerId = buyerId;
    this.skuId = skuId;
    this.skuPrice = skuPrice;
    this.skuQty = skuQty;
    setAmount(skuPrice.multiply(BigDecimal.valueOf(skuQty)));
    setPaymentInfo(PaymentInfo.UNPAID_INFO);
    notifyOrderThatCreated();
  }

  public boolean isUnpaid() {
    return getPaymentInfo().getStatus().equals(PaymentInfo.Status.UNPAID);
  }

  public boolean isPaidFail() {
    return getPaymentInfo().getStatus().equals(PaymentInfo.Status.FAIL);
  }

  public boolean isPaidSuccess() {
    return getPaymentInfo().getStatus().equals(PaymentInfo.Status.SUCCESS);
  }


  protected void paidSuccess(String serialNumber) {
    if (!isPaidSuccess()) {
      setPaymentInfo(new PaymentInfo(PaymentInfo.Status.SUCCESS, serialNumber));
      notifyOrderThatPaidSuccess();
    }
  }

  protected void paidFail(String serialNumber) {
    if (isUnpaid()) {
      setPaymentInfo(new PaymentInfo(PaymentInfo.Status.FAIL, serialNumber));
    }
  }

  private void notifyOrderThatCreated() {
    new OrderCreatedEvent(this, getSkuId(), getSkuQty());
  }

  private void notifyOrderThatPaidSuccess() {
    new OrderPaidSuccessEvent(this, getSkuId(), getSkuQty());
  }

}
