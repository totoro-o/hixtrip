package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

/**
 * 这是请求体的示例
 */
@Data
@Builder
public class SampleReq {

    private String test;
}
