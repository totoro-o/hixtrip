package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.sample.model.MallOrder;
import com.hixtrip.sample.infra.db.dataobject.MallOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MallOrderConverter {

    MallOrderConverter INSTANCE = Mappers.getMapper(MallOrderConverter.class);

    MallOrder doToDomain(MallOrderDO value);
}
