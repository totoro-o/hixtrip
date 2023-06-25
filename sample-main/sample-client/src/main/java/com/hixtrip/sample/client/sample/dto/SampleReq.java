package com.hixtrip.sample.client.sample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这是请求体的示例
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SampleReq {

    private String test;
}
