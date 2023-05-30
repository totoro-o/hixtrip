package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

  @Autowired
  OrderDOMapper orderDOMapper;

  @Override
  public Order get(Long id) {
    OrderDO orderDO = orderDOMapper.selectByPrimaryKey(id);
    return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
  }

  @Override
  public Order save(Order order) {
    orderDOMapper.insert(OrderDOConvertor.INSTANCE.domainToDo(order));
    return order;
  }

}
