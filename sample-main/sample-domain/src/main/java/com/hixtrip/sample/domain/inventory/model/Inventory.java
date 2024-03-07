package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {


    /**
     * SkuId
     */
    private String skuId;
    //可用
    private Long sellableQuantity;
    //预占
    private Long withholdingQuantity;
    //占用
    private Long occupiedQuantity;

    //可以用库存->预占库存
    public void lockInventory(Long sellableQuantity,Long withholdingQuantity,Long amount){
        this.sellableQuantity = sellableQuantity - amount;
        this.withholdingQuantity = withholdingQuantity + amount;
    }

    //预占库存->占用库存
    public void occupyInventory(Long withholdingQuantity,Long occupiedQuantity,Long amount){
        this.withholdingQuantity = withholdingQuantity - amount;
        this.occupiedQuantity = occupiedQuantity + amount;
    }
}
