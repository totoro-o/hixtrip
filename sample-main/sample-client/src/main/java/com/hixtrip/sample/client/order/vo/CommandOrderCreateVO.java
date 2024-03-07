package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class CommandOrderCreateVO {
    //订单号
    private String id;
    private String code;
    private String msg;
}
