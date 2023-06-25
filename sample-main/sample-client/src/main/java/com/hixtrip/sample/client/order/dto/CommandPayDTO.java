package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建订单的请求
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    private String orderId;

    private String payStatus;


}
