package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.pay.PaymentStrategy;
import org.springframework.context.ApplicationEvent;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
// @Event #注解方式发布事件
public class OrderPaidSuccessEvent extends ApplicationEvent {
  private Long skuId;
  private Long qty;


  public OrderPaidSuccessEvent(Order source, Long skuId, Long qty) {
    super(source);
    this.skuId = skuId;
    this.qty = qty;
  }


  public Long getSkuId() {
    return skuId;
  }

  public Long getQty() {
    return qty;
  }


}
