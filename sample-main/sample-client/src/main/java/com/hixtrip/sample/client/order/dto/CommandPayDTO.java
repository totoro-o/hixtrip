package com.hixtrip.sample.client.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    /**
     * 订单id
     */
    @NotBlank(message = "orderId不能为空！")
    private String orderId;

    /**
     * 支付状态（paySuccess,payFail,payRepeat）
     */
    @NotBlank(message = "payStatus不能为空！")
    private String payStatus;


}
