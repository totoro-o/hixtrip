package com.hixtrip.sample.domain.commodity.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * 商品数据
 */
@Data
@SuperBuilder
public class Commodity {

    /**
     * SKU_ID
     */
    private String skuId;

    /**
     * 价格
     */
    private Long price;

}
