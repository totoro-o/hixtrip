package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.constants.OrderPayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Order {

    /**
     * 订单号
     */
    private String id;


    /**
     * 购买人
     */
    private String userId;


    /**
     * SkuId
     */
    private String skuId;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 购买金额
     */
    private BigDecimal money;

    /**
     * 购买时间
     */
    private LocalDateTime payTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 初始化订单
     */
    public static Order init(String id, String userId, String skuId, Integer amount, BigDecimal money) {
        Order order = new Order();
        order.setId(id);
        order.setSkuId(skuId);
        order.setAmount(amount);
        order.setMoney(money);
        order.setUserId(userId);

        order.setPayStatus(OrderPayStatusEnum.UNPAID.getCode());

        order.setCreateBy(userId);
        order.setCreateTime(LocalDateTime.now());
        order.setDelFlag(0L);
        return order;
    }

    /**
     * 支付成功
     */
    public void paySuccess() {
        // 支付状态校验
        if (!OrderPayStatusEnum.UNPAID.getCode().equals(this.getPayStatus())) {
            throw new IllegalArgumentException("支付状态不合法");
        }
        this.setPayStatus(OrderPayStatusEnum.PAID.getCode());
        this.setPayTime(LocalDateTime.now());

        // TODO: 使用拦截器更新
        this.setUpdateBy("");
        this.setUpdateTime(LocalDateTime.now());
    }

    /**
     * 支付失败
     */
    public void payFail() {
        // 支付状态校验
        if (!OrderPayStatusEnum.UNPAID.getCode().equals(this.getPayStatus())) {
            throw new IllegalArgumentException("支付状态不合法");
        }

        this.setPayStatus(OrderPayStatusEnum.PAY_FAIL.getCode());
        this.setPayTime(LocalDateTime.now());

        // TODO: 使用拦截器更新
        this.setUpdateBy("");
        this.setUpdateTime(LocalDateTime.now());
    }
}
