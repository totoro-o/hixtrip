package com.hixtrip.sample.infra.db.dataobject;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName(value = "order", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class OrderDO extends SampleDO {

    /**
     * 订单ID
     */
    @TableId
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
     * 支付ID
     */
    private Long payId;

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
     * 订单完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 用户备注
     */
    private String userRemark;


}
