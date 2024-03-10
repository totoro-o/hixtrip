package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 支付回调返回值
 */
@Data
@Builder
public class PayCallbaskVO {
    /**
     * 订单号
     */
    private String id;

    /**
     * 返回值状态
     */
    private String resultStatus;

    /**
     * 返回信息
     */
    private String resultMsg;
}
