package com.hixtrip.sample.client.sample.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class SampleVO {
    private String id;
    private String name;
    private String code;
    private String msg;
}
