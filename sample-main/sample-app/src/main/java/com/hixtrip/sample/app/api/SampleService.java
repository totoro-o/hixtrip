package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.BaseVO;

/**
 * 这是一个示例, app层定义业务接口,entry 调用
 */
public interface SampleService {
    BaseVO test(SampleReq sampleReq);
}
