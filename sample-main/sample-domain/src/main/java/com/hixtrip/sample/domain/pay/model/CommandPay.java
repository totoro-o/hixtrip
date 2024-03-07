package com.hixtrip.sample.domain.pay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    /**
     * 订单id
     * TODO: 使用订单id作为支付域的ID可能出现，一次订单多次支付的问题，此处默认订单有且只有一次支付
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String payStatus;
}