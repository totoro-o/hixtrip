package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class SampleRepositoryImpl implements SampleRepository {
    @Override
    public Sample test() {
        //此处可调用mybatis进行查询，DO转化为领域对象返回，如：
        //var sampleDo = ....
        //var sample = SampleConvertor.convert(sampleDo)
        return Sample.builder().build();
    }
}
