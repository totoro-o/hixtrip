package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.inventory.model.Sku;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.dataobject.SkuDo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * DO对像 -> 领域对象转换器
 * todo 自由实现
 */
@Mapper
public interface SkuDOConvertor {
    SkuDOConvertor INSTANCE = Mappers.getMapper(SkuDOConvertor.class);

    Sku doToDomain(SkuDo skuDo);
}
