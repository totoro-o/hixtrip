package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.SampleVO;

/**
 * 这是一个示例, app层定义业务接口,entry 调用
 */
public interface SampleService {
    SampleVO test(SampleReq sampleReq);
}
