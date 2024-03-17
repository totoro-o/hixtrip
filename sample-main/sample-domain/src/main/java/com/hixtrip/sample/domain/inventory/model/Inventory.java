package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 库存表
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {

    /**
     * 商品规格id
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
     * 占用库存
     */
    private Long occupiedQuantity;

    public Inventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        this.skuId = skuId;
        this.sellableQuantity = sellableQuantity;
        if (this.sellableQuantity == null) {
            this.sellableQuantity = 0L;
        }
        this.withholdingQuantity = withholdingQuantity;
        if (this.withholdingQuantity == null) {
            this.withholdingQuantity = 0L;
        }
        this.occupiedQuantity = occupiedQuantity;
        if (this.occupiedQuantity == null) {
            this.occupiedQuantity = 0L;
        }
    }

    /**
     * 可用库存
     *
     * @return
     */
    public Long availableQuantity() {
        return this.sellableQuantity - this.withholdingQuantity - this.occupiedQuantity;
    }

}
