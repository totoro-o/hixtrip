package com.hixtrip.sample.domain.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 09:50
 * 订单Domain创建命令类
 */
@Getter
@Builder
public class OrderDomainCreateCmd {

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
     * 购买人
     */
    private String userId;
}
