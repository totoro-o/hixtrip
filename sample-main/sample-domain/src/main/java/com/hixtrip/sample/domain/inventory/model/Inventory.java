package com.hixtrip.sample.domain.inventory.model;

import com.hixtrip.sample.domain.inventory.exception.InventoryDomainException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:04
 * 库存领域，忽略仓库、库存品、计量单位等业务
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {

    /**
     * skuId
     */
    private String skuId;

    /**
     * 可售库存
     */
    private Long sellableQuantity;

    /**
     * 预占库存
     */
    private Long withholdingQuantity;

    /**
     * 预占库存
     */
    private Long occupiedQuantity;


    /**
     * 预占库存：初期下单时设定值，sellableQuantity减，withholdingQuantity加
     * @param withholdingQuantity
     */
    public void updateWithholdingQuantity(Long withholdingQuantity) {
        long updateSellableQuantity = this.sellableQuantity - withholdingQuantity;
        if (updateSellableQuantity < 0) {
            throw new InventoryDomainException("预占库存不能大于可售库存");
        }
        this.sellableQuantity = updateSellableQuantity;
        this.withholdingQuantity = this.withholdingQuantity + withholdingQuantity;
    }

    /**
     * 占用库存：支付成功回调设定值，withholdingQuantity减， occupiedQuantity加
     * @param occupiedQuantity
     */
    public void updateOccupiedQuantity(Long occupiedQuantity) {
        long updateWithholdingQuantity = this.withholdingQuantity - occupiedQuantity;
        if(updateWithholdingQuantity < 0){
            throw new InventoryDomainException("占用库存不能大于预占库存");
        }
        this.withholdingQuantity = updateWithholdingQuantity;
        this.occupiedQuantity = this.occupiedQuantity + occupiedQuantity;
    }
}
