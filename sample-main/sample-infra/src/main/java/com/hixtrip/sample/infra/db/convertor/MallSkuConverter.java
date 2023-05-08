package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.sample.model.MallSku;
import com.hixtrip.sample.infra.db.dataobject.MallSkuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MallSkuConverter {

    MallSkuConverter INSTANCE = Mappers.getMapper(MallSkuConverter.class);

    MallSku doToDomain(MallSkuDO sampleDO);
}
