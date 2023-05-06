package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;

public interface OrderMapper {
    int save(OrderDO orderDO);
}
