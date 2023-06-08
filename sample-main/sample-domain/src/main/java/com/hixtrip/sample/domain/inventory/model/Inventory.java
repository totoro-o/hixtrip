package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 库存对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    private String skuId;
    private Long sellableQuantity;
    private Long withholdingQuantity;
    private Long occupiedQuantity;


}
