package com.hixtrip.sample.domain.inventory;

import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.OrderCreatedEvent;
import com.hixtrip.sample.domain.order.OrderPaidSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 库存领域服务
 * 库存设计，忽略仓库、库存品、计量单位等业务
 */
@Component
public class InventoryDomainService {

  @Autowired
  InventoryRepository inventoryRepository;

  /**
   * 获取sku当前库存
   *
   * @param skuId
   */
  public Inventory getInventory(Long skuId) {
    return inventoryRepository.get(skuId);
  }

  /**
   * 订单生成时，库存变化
   *
   * @param event
   */
  @EventListener
  public void onOrderCreatedEvent(OrderCreatedEvent event) {
    Inventory inventory = getInventory(event.getSkuId());
    inventory.transferSellQty2holdingQty(event.getQty());
    inventoryRepository.save(inventory);
  }

  /**
   * 订单支付成功
   *
   * @param event
   */
  @EventListener
  void onOrderPaidSuccessEvent(OrderPaidSuccessEvent event) {
    Inventory inventory = getInventory(event.getSkuId());
    inventory.transferHoldingQty2OccupiedQuantity(event.getQty());
    inventoryRepository.save(inventory);
  }

}
