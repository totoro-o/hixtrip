package com.hixtrip.sample.domain.order.model;

import com.hixtrip.sample.domain.order.enums.PayStatusEnum;
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
     * 创建订单
     */
    public void createOrder() {
        this.payStatus = PayStatusEnum.initStatus().getValue();
        this.createBy = this.userId;
        this.updateBy = this.userId;
    }


    /**
     * 支付订单 支付成功
     */
    public void paySuccess() {
        this.payStatus = PayStatusEnum.SUCCESS.getValue();
        this.updateTime = LocalDateTime.now();
    }

    /**
     * 支付订单 支付失败
     */
    public void payFail() {
        this.payStatus = PayStatusEnum.FAIL.getValue();
        this.updateTime = LocalDateTime.now();
    }


}
