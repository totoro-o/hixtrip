package com.hixtrip.sample.app.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderVo {

    /**
     * userId
     */
    private String userId;

    /**
     * skuid
     */
    private String skuId;

    /**
     * sku数量
     */
    private Long skuNumber;

}
