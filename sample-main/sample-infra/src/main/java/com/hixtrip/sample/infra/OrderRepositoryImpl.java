package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDOConvertor orderDOConvertor;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryRepositoryImpl inventoryRepository;



    @Override
    public void createOrder(Order order) {
        //参数校验
        if(order == null){
           throw new RuntimeException("订单为空");
        }
        //获取线程用户名
        String username="1";
        //使用分布式锁加锁保证每个用户同时只有一个线程进行下单 其他用户直接拒绝
        //加锁防止重复下单
        //加锁成功设置过期时间防止死锁
        //查询购买总金额和数量
        BigDecimal totalMoney = commodityDomainService.getSkuPrice(order.getSkuId());
        //新增订单信息
        order.setTotalAmount(totalMoney);
        order.setOrderStatus("未支付");
        order.setUserId(username);
        order.setCreateTime(new Date());
        order.setExpireTime(new Date(System.currentTimeMillis() + 1800000));
        order.setProcessStatus("创建订单，未支付");
        OrderDO orderDO = orderDOConvertor.INSTANCE.toOrderDO(order);
        int saveRows = orderMapper.saveOrder(orderDO);
        if (saveRows <= 0) {
            throw new RuntimeException("创建订单失败");
        }
        //订单表新增成功,获取订单号
        //补全订单详情的信息
        //扣减库存
        //扣减库存
        boolean inventoryDeducted = inventoryRepository.deductInventory(order.getSkuId(), order.getSkuNum());
        if (!inventoryDeducted) {
            throw new RuntimeException("库存扣减失败");
        }
        //通过rabbitmq发送延迟消息 半个小时以后订单没有支付超时取消订单 回滚库存


    }

    @Override
    public void updateOrder(Order order) {
        OrderDO orderDO = orderDOConvertor.INSTANCE.toOrderDO(order);
        int updateRows = orderMapper.updateOrder(orderDO);
        if (updateRows <= 0) {
            throw new RuntimeException("修改订单失败");
        }
    }

    @Override
    public Order findByOrderNumber(String orderNumber) {
        OrderDO orderDO = orderMapper.findByOrderNumber(orderNumber);
        Order order = orderDOConvertor.INSTANCE.toOrder(orderDO);
        return order;
    }

    @Override
    public String findOrderNumberByOrderId(Long id) {
        return orderMapper.findOrderNumberByOrderId(id);
    }
}
