package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.SampleVO;

public interface SampleService {
    SampleVO test(SampleReq sampleReq);
}
