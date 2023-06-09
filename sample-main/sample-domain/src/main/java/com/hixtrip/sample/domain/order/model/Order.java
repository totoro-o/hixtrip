package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.em.EnumOrderStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class Order {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单地址信息
     */
    private Long orderAddressId;

    /**
     * 订单流水号
     */
    private String orderNo;

    /**
     * 订单状态 0-待付款 1-待发货 2-待收货 3-已完成 4-已取消
     */
    private Integer orderStatus;

    /**
     * 订单金额 (单位：分)
     */
    private Long totalAmount;

    /**
     * 实际需支付金额 (单位：分)
     */
    private Long payAmount;

    /**
     * 优惠金额 (单位：分)
     */
    private Long preferentialAmount;

    /**
     * 商品数量
     */
    private Long quantity;

    /**
     * 用户备注
     */
    private String userRemark;

    private Order() {

    }

    public static Order instance(String orderNo, Long payAmount, OrderCreate orderCreate, List<OrderItem> orderItemList) {
        Long totalAmount = orderItemList.stream().mapToLong(OrderItem::getCommodityTotalPrice).sum();
        return Order.builder()
                .orderNo(orderNo)
                .userId(orderCreate.getUserId())
                .orderStatus(EnumOrderStatus.WAIT_PAY.getStatus())
                .payAmount(Math.max(payAmount, 0L))
                .preferentialAmount(Math.max(totalAmount - payAmount, 0L))
                .totalAmount(Math.max(totalAmount, 0L))
                .quantity((long) orderItemList.size())
                /* 其余数据赋值  */
                .build();
    }


}
