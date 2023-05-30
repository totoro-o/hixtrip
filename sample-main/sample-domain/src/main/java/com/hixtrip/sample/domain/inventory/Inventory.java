package com.hixtrip.sample.domain.inventory;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/29
 * @since
 */

@Data
@Builder
public class Inventory {
  private Long id;

  //@Version 在一般并发场景下避免库存超卖
  private Long version;

  private Long creatorId;
  private Instant createdTime;
  private Long modifierId;
  private Instant modifiedTime;
  private Long skuId;
  private Long sellableQuantity;
  private Long withholdingQuantity;
  private Long occupiedQuantity;

  // 可售库存必须大于下单商品数量,才能生成订单
  public boolean isEnoughToSell(Long buyQty) {
    return !(sellableQuantity.compareTo(buyQty) < 0);
  }

  // 可售库存数量转移到预占库存里
  protected Inventory transferSellQty2holdingQty(Long qty) {
    setSellableQuantity(sellableQuantity - qty);
    setWithholdingQuantity(withholdingQuantity + qty);
    return this;
  }

  // 预占用库存数量转移到占用库存
  protected Inventory transferHoldingQty2OccupiedQuantity(Long qty) {
    setOccupiedQuantity(occupiedQuantity + qty);
    setWithholdingQuantity(withholdingQuantity - qty);
    return this;
  }



}
