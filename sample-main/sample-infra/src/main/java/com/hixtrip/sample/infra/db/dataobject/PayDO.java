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
@TableName(value = "pay", autoResultMap = true)
@SuperBuilder(toBuilder = true)
public class PayDO extends SampleDO{

    /**
     * 支付ID
     */
    @TableId
    private Long payId;

    /**
     * 支付平台 0-支付宝 1-微信 ...
     */
    private Integer payPlatform;

    /**
     * 支付流水号
     */
    private String payNo;

    /**
     * 支付金额 (单位：分)
     */
    private Long amount;

    /**
     * 支付状态 1-支付成功 2-支付失败
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付备注
     */
    private String payRemark;

    /**
     * 支付回调参数
     */
    private String notifyParams;



}
