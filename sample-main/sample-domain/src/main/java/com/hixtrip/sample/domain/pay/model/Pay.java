package com.hixtrip.sample.domain.pay.model;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class Pay {

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

    /**
     * 支付回调异常信息
     */
    private String notifyException;
}
