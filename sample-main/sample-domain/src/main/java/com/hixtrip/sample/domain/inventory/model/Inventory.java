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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Inventory {
    /**
     * SkuId
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
}
