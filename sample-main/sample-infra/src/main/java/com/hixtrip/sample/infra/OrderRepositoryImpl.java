package com.hixtrip.sample.infra;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hixtrip.sample.common.enums.OrderPayStatusEnum;
import com.hixtrip.sample.common.exception.BizException;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public int save(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.orderToOrderDO(order);
        return orderMapper.insert(orderDO);
    }

    @Override
    public void updateOrder(CommandPay commandPay,String orderPayStatus) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        if (orderDO==null){
            throw new BizException(-1,"订单不存在！");
        }
        orderDO.setPayStatus(orderPayStatus);
        orderDO.setPayTime(LocalDateTime.now());
        orderMapper.update(orderDO,new QueryWrapper<OrderDO>().eq("id",orderDO.getId()));
    }
}
