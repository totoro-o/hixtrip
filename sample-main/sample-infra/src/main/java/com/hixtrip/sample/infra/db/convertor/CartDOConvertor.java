package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.cart.model.Cart;
import com.hixtrip.sample.infra.db.dataobject.CartDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartDOConvertor {

    CartDOConvertor INSTANCE = Mappers.getMapper(CartDOConvertor.class);

    Cart doToDomain(CartDO cartDO);
}
