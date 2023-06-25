package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class SampleRepositoryImpl implements SampleRepository {

    @Autowired
    private SampleMapper sampleMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Sample test() {
        redisTemplate.opsForValue().set("test", "123");
        //此处可调用mybatis进行查询，DO转化为领域对象返回，如：
        SampleDO sampleDO = sampleMapper.selectSample();
        SampleDO sampleDO1 = sampleMapper.selectById(1L);
        return SampleDOConvertor.INSTANCE.doToDomain(sampleDO);
    }
}
