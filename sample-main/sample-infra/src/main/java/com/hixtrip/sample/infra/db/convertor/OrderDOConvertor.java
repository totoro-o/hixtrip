package com.hixtrip.sample.infra.db.convertor;

import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderDOConvertor {
    OrderDOConvertor INSTANCE = Mappers.getMapper(OrderDOConvertor.class);

    /**
     * 把领域层对象转化为基础层对象
     * @param order
     * @return
     */
    OrderDO toOrderDO(Order order);

    /**
     * 把基础层对象转化为领域层对象
     * @param orderDO
     * @return
     */
    Order toOrder(OrderDO orderDO);
}
