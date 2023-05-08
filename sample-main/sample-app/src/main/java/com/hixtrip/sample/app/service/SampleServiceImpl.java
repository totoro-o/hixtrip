package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.BaseVO;
import org.springframework.stereotype.Component;

/**
 * 这是一个示例, app层负责处理request请求，调用领域服务
 */
@Component
public class SampleServiceImpl implements SampleService {
    @Override
    public BaseVO test(SampleReq sampleReq) {
        return BaseVO.builder().build();
    }
}
