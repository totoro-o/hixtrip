package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.annotation.CallBackStatus;
import com.hixtrip.sample.domain.order.annotation.OrderCallBack;
import com.hixtrip.sample.domain.order.annotation.OrderStatus;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
@OrderCallBack
public class OrderDomainService {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(Order order,  BigDecimal skuPrice) throws Exception {
        //需要你在infra实现, 自行定义出入参
        return order.createOrder(skuPrice);
    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    @OrderCallBack(CallBackStatus.SUCCESS)
    public Boolean orderPaySuccess(Order order) {
        //需要你在infra实现, 自行定义出入参
        //todo 支付参数秘钥校验 失败返回
        //todo 锁机制 简便就先假设为单体项目只要在程序中锁住就行，分布式下用分布式锁，保证幂等
        synchronized (order.getOrderNumber()){
            Order order1 = order.getOrderByOrderNumber(order.getOrderNumber());
            //也有应该是正在支付的状态，只有这个状态才能处理支付回调
            if(OrderStatus.NO_PAY.getCode().equals(order1.getOrderStatus())){
                //处理订单
                order1.orderToSuccess(order);
                Inventory inventory = inventoryDomainService.getBySkuId(order1.getSkuId());
                //处理库存
                inventory.changeIntentoryOnPaySuccess(order.getSkuNumber());
                return true;
            } else {
                //非未支付，已经处理过了，返回
                return true;
            }
        }
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    @OrderCallBack(CallBackStatus.FAIL)
    public Boolean orderPayFail(Order order) {
        //需要你在infra实现, 自行定义出入参
        //todo 支付参数秘钥校验 失败返回
        //todo 锁机制 简便就先假设为单体项目只要在程序中锁住就行，分布式下用分布式锁 保证幂等
        synchronized (order.getOrderNumber()){
            Order order1 = order.getOrderByOrderNumber(order.getOrderNumber());
            //也有应该是正在支付的状态，只有这个状态才能处理支付回调
            if(OrderStatus.NO_PAY.getCode().equals(order1.getOrderStatus())){
                //处理订单
                order1.orderToFail();
                Inventory inventory = inventoryDomainService.getBySkuId(order1.getSkuId());
                //处理库存
                inventory.changeIntentoryOnPayFail(order.getSkuNumber());
                return true;
            } else {
                //非未支付，已经处理过了，返回
                return true;
            }
        }
    }
}
