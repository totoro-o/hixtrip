package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.infra.db.dataobject.OrderDO;

public interface OrderMapper {

    String findOrderIdByCaseNo(String caseNo);

    int insertIfNotExist(OrderDO orderDO);

    int updateOrderStatus(Long orderId, Integer sourceOrderStatus, Integer afterOrderStatus);
}
