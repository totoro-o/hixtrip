package com.hixtrip.sample.infra.db.mapper;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 订单mapper映射
 */
@Mapper
public interface OrderMapper {
    int saveOrder(OrderDO orderDO);

    int updateOrder(OrderDO orderDO);

    OrderDO findByOrderNumber(String orderNumber);

    String findOrderNumberByOrderId(Long id);

}
