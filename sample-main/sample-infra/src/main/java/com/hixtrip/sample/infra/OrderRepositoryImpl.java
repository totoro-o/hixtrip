package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Integer save(Order order) {

        OrderDO orderDO = OrderDOConvertor.INSTANCE.orderToOrderDo(order);

        return orderMapper.insert(orderDO);
    }
}
