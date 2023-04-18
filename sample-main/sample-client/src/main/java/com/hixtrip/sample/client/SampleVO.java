package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

/**
 * 这是返回值的示例
 */
@Data
@Builder
public class SampleVO {
    private String code;
    private String msg;
}
