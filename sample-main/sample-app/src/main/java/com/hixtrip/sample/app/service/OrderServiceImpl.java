package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.client.OrderVO;
import com.hixtrip.sample.client.PayCallback;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public class OrderServiceImpl implements OrderService {
  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  InventoryDomainService inventoryDomainService;
  @Autowired
  OrderDomainService orderDomainService;
  @Autowired
  CommodityDomainService commodityDomainService;

  @Autowired
  PayDomainService payDomainService;

  @Override
  public OrderVO create(OrderReq req) {
    Long userId = req.getUserId();
    Long qty = req.getQty();
    Long skuId = req.getSkuId();
    Inventory inventory = inventoryDomainService.getInventory(skuId);
    if (inventory.isEnoughToSell(qty)) {
      BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
      Order order = orderDomainService.createOrder(skuPrice, skuId, userId, qty);
      return OrderVO.builder().msg("订单生成成功").orderId(order.getId()).build();
    } else {
      return OrderVO.builder().msg("可售库存不足,订单生成失败").build();
    }
  }

  @Override
  public void payCallback(PayCallback payCallback) {
    PaymentStrategy paymentStrategy =
            applicationContext.getBeansOfType(PaymentStrategy.class).values().stream()
                    .filter(p -> p.test(PaymentStrategy.PaymentStatus.valueOf(payCallback.getStatus())))
                    .findFirst().orElseThrow();
    paymentStrategy.processPayment(payCallback.getOrderId(), payCallback.getPaymentNumber());
  }


}
