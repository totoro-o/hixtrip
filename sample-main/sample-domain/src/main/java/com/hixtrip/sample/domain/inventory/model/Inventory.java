package com.hixtrip.sample.domain.inventory.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 库存对应实体（充血设计）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {
    /**
     * skuId
     */
    private String skuId;
    /**
     * 可用库存数量
     */
    private Long sellableQuantity;
    /**
     * 预占库存数量
     */
    private Long withholdingQuantity;
    /**
     * 占用库存数量
     */
    private Long occupiedQuantity;



    /**
     * 预占用库存
     */
    public static Boolean withHolding(Inventory inventory, Integer amount){
        if (inventory == null || inventory.getSellableQuantity() < amount) {
            //返回库存不足
            return false;
        }
        return true;
    }

    /**
     * 占用库存
     */
    public void occupied(){

    }

    public Inventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        this.skuId = skuId;
        this.sellableQuantity = sellableQuantity;
        this.withholdingQuantity = withholdingQuantity;
        this.occupiedQuantity = occupiedQuantity;
    }
}
