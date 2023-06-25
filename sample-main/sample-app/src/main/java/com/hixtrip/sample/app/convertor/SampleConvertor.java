package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.sample.vo.SampleVO;
import com.hixtrip.sample.domain.sample.model.Sample;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SampleConvertor {

    SampleConvertor INSTANCE = Mappers.getMapper(SampleConvertor.class);

    SampleVO sampleToSampleVO(Sample sample);
}
