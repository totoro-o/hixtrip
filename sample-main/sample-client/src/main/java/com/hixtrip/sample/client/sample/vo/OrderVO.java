package com.hixtrip.sample.client.sample.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class OrderVO {
    private String id;
    private String code;
    private String msg;
}
