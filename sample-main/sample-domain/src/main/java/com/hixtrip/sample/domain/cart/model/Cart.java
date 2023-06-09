package com.hixtrip.sample.domain.cart.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Cart {

    /**
     * 购物车ID
     */
    private Long cartId;

    /**
     * 商品ID
     */
    private String skuId;

    /**
     * 商品数量
     */
    private Long num;

    /**
     * 商品单价
     */
    private Long price;
    // ....
    // 商品基本信息


    /**
     * 计算总价
     */
    public Long totalPrice() {
        return price * num;
    }

}
