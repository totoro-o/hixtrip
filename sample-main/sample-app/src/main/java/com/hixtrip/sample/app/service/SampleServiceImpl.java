package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.app.convertor.SampleConvertor;
import com.hixtrip.sample.client.sample.dto.SampleReq;
import com.hixtrip.sample.client.sample.vo.SampleVO;
import com.hixtrip.sample.domain.sample.SampleDomainService;
import com.hixtrip.sample.domain.sample.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这是一个示例, app层负责处理request请求，调用领域服务
 */
@Component
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleDomainService sampleDomainService;

    @Override
    public SampleVO test(SampleReq sampleReq) {
        Sample sample = sampleDomainService.test();
        return SampleConvertor.INSTANCE.sampleToSampleVO(sample);
    }
}
