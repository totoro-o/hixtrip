package com.hixtrip.sample.domain.sample;

import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 这是领域服务的示例，领域服务只处理域内业务, 返回的领域对象由DO转换
 */
@Component
public class SampleDomainService {
    @Autowired
    private SampleRepository sampleRepository;

    public Sample test() {
        return sampleRepository.test();
    }
}
