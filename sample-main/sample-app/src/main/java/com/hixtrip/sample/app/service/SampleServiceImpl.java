package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.SampleVO;
import org.springframework.stereotype.Component;

/**
 * 这是一个示例, app层负责处理request请求，调用领域服务
 */
@Component
public class SampleServiceImpl implements SampleService {
    @Override
    public SampleVO test(SampleReq sampleReq) {
        return SampleVO.builder().build();
    }
}
