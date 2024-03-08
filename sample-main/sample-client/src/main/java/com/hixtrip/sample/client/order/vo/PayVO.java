package com.hixtrip.sample.client.order.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class PayVO {
    private String id;
    private String name;
    private String code;
    private String msg;
}
