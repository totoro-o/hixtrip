package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.domain.cart.CartDomainService;
import com.hixtrip.sample.domain.cart.model.Cart;
import com.hixtrip.sample.domain.em.EnumOrderStatus;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.model.OrderCreate;
import com.hixtrip.sample.domain.order.model.OrderItem;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartDomainService cartDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public Order createOrder(OrderCreate orderCreate) {
        //需要你在infra实现, 自行定义出入参

        //获取选中购物车数据
        List<Cart> cartList = cartDomainService.findListByIdListAndCheckSku(orderCreate.getCartIdList());
        //初始化订单商品数据
        List<OrderItem> orderItemList = cartList.stream().map(OrderItem::instance).collect(Collectors.toList());


        //创建订单 生成订单号，计算支付金额(计算优惠)
        String caseNo = orderRepository.createOrderNo(orderCreate.getUserId());
        Long payAmount = orderRepository.calcPayAmount(orderCreate.getUserId(), orderItemList);
        Order order = Order.instance(caseNo, payAmount, orderCreate, orderItemList);
        boolean createSuccess = orderRepository.insertIfNotExist(order);
        if (!createSuccess) {
            throw new RuntimeException("创建订单失败");
        }

        //扣库存
        for (OrderItem orderItem : orderItemList) {
            boolean success = inventoryDomainService.changeInventory(orderItem.getSkuId(), null, null, orderItem.getQuantity());
            if (!success) {
                throw new RuntimeException("创建订单失败,库存不足");
            }
        }

        //创建明细
        orderItemList.forEach(val -> val.setOrderId(order.getOrderId()));
        orderRepository.createItemList(orderItemList);

        //删除购物车
        cartDomainService.delByIds(orderCreate.getCartIdList());

        return order;
    }


    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public boolean orderPaySuccess(Pay pay, Long orderId) {
        //需要你在infra实现, 自行定义出入参
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            //更新订单状态
            boolean success = orderRepository.updateOrderStatus(orderId, order.getOrderStatus(), EnumOrderStatus.WAIT_SEND_EXPRESS.getStatus());
            if (success) {
                //支付成功
                //更新  order 支付id 基本业务字段

                //库存是否等发货后再更新？

                //mq通知发货
            }
        }
        return true;
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public boolean orderPayFail(Pay pay, Long orderId) {
        //需要你在infra实现, 自行定义出入参

        Order order = orderRepository.findById(orderId);
        if (order != null) {
            //更新订单状态
            boolean success = orderRepository.updateOrderStatus(orderId, order.getOrderStatus(), EnumOrderStatus.CANCEL.getStatus());
            if (success) {
                //支付失败
                //更新  order  基本业务字段


                //库存归还
                List<OrderItem> orderItemList = orderRepository.findOrderItemList(orderId);
                for (OrderItem orderItem : orderItemList) {
                    inventoryDomainService.changeInventory(orderItem.getSkuId(), null, null,
                            Math.negateExact(orderItem.getQuantity()));
                }


            }
        }
        return true;
    }
}
