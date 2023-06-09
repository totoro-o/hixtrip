package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.cart.model.Cart;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class OrderItem {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private String skuId;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 商品单价 (单位：分)
     */
    private Long commodityPrice;

    /**
     * 商品总价 (单位：分)
     */
    private Long commodityTotalPrice;

    /**
     * 商品基础数据 如名称、图片
     * ...
     */

    private OrderItem() {
    }

    /**
     * 计算总价
     */
    public Long getCommodityTotalPrice() {
        return this.commodityPrice * this.quantity;
    }

    public static OrderItem instance(Cart cart) {
        return OrderItem.builder().build();
    }
}
