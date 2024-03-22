package com.hixtrip.sample.domain.order.model;

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
     * 卖家id
     */
    private String sellerId;

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
     * 支付状态(wait支付中;paying支付中;success支付成功;failed支付失败)
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

    public void create(){
        this.createTime = LocalDateTime.now();
        this.createBy = this.userId;
        this.delFlag = 0l;
        this.payStatus = "wait";//待支付
    }

    public void paying(){
        this.payStatus = "paying";//支付中
        this.payTime = LocalDateTime.now();
    }

    public void paySuccess(){
        this.payStatus = "success";//支付中
        this.updateTime = LocalDateTime.now();
    }
    public void payFailed(){
        this.payStatus = "failed";//支付中
        this.updateTime = LocalDateTime.now();
    }
}
