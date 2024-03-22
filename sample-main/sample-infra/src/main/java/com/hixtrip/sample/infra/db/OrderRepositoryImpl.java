package com.hixtrip.sample.infra.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.mapper.SampleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Order createOrder(Order order) {
        orderMapper.insert(order);

        userOrderSyncToCache(order.getUserId());

        return order;
    }

    @Override
    public void orderPaySuccess(String orderId) {
        var order = orderMapper.selectById(orderId);
        if(order.getPayStatus().equals("success"))
            throw new RuntimeException("订单重复支付");
        //支付成功的逻辑
        order.paySuccess();
        orderMapper.updateById(order);

        //更新缓存:买家的订单信息
        userOrderSyncToCache(order.getUserId());
    }

    @Override
    public void orderPayFail(String orderId) {
        var order = orderMapper.selectById(orderId);
        order.payFailed();
        orderMapper.updateById(order);

        //更新缓存:买家的订单信息
        userOrderSyncToCache(order.getUserId());
    }

    //同步用户的订单信息到缓存
    private void userOrderSyncToCache(String userId){
        //更新缓存:买家的订单信息
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getUserId,userId);
        queryWrapper.eq(Order::getDelFlag,0l);
        var orderList = orderMapper.selectList(queryWrapper);
        redisTemplate.opsForValue().set("user_order_List_"+userId,orderList);
    }
}
