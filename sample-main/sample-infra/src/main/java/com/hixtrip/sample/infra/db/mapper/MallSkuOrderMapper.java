package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.MallOrderDO;

public interface MallSkuOrderMapper {

    void saveOrUpdate(MallOrderDO order);

    MallOrderDO findOrderByOrderId(Long orderId);

    void updateOrderStatus(Long orderId, int status);
}
