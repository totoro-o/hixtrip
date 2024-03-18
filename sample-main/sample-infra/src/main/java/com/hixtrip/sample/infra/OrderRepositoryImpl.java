package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Transactional
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ReentrantLock reentrantLock;


    @Override
    public Order createOrder(Order order) {
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(order, orderDO);
        orderMapper.insert(orderDO);
        return OrderDOConvertor.INSTANCE.doToDomain(orderDO);
    }

    @Override
    public Map<Object, Object> getOrder(String orderId) {
        try {
            Map<Object, Object> orderMap = redisTemplate.opsForHash().entries(orderId);
            if (orderMap.isEmpty()) {
                //避免缓存击穿
                if (reentrantLock.tryLock()) {
                    OrderDO orderDO = orderMapper.getOrder(orderId);
                    Map<String, String> map = new HashMap<>();
                    map.put("skuId", orderDO.getSkuId());
                    map.put("amount", orderDO.getAmount().toString());
                    redisTemplate.opsForHash().putAll(orderId, map);
                } else {
                    Thread.sleep(100);
                }
                return getOrder(orderId);
            }
            return orderMap;
        } catch (Exception e) {
            e.printStackTrace();
            reentrantLock.unlock();
            throw new RuntimeException("获取订单失败");
        }
    }


    @Override
    public void delOrderById(String orderId) {
        OrderDO order = OrderDO.builder().id(Integer.valueOf(orderId)).build();
        orderMapper.deleteById(order);
    }
}
