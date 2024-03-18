package com.hixtrip.sample.domain.inventory.dto;

import lombok.Builder;

@Builder
public class InventoryDto {

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
