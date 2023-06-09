package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.CartDO;

import java.util.List;

public interface CartMapper {
    List<CartDO> listByCartIdList();

}
