package com.hixtrip.sample.domain.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    private Long id;

    private String skuId;

    private Long totalQuantity;

    private Long sellableQuantity;

    private Long withholdingQuantity;

    private Long occupiedQuantity;

}
