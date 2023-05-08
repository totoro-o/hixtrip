package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.sample.model.MallStock;
import com.hixtrip.sample.infra.db.dataobject.MallStockDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MallStockConverter {

    MallStockConverter INSTANCE = Mappers.getMapper(MallStockConverter.class);

    MallStock doToDomain(MallStockDO value);
}
