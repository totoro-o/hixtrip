package com.hixtrip.sample.client.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * 创建订单的请求 入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandOderCreateDTO {

    /**
     * 商品规格id
     */
    @NotBlank(message = "skuId不能为空！")
    private String skuId;

    /**
     * 购买数量
     */
    @NotBlank(message = "amount不能为空！")
    private Integer amount;

    /**
     * 购买金额
     */
    @NotBlank(message = "money不能为空！")
    private BigDecimal money;

    /**
     * 卖家
     */
    @NotBlank(message = "sellerId不能为空！")
    private String sellerId;


}
