package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.client.PayCallbackReq;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    InventoryDomainService inventoryDomainService;
    @Autowired
    CommodityDomainService commodityDomainService;
    @Autowired
    OrderDomainService orderDomainService;
    @Autowired
    PayDomainService payDomainService;

    @Override
    public Order create(OrderReq orderReq, Long userId) {
        String skuId = orderReq.getSkuId();
        Long inventory = inventoryDomainService.getInventory(skuId);
        Long num = orderReq.getNum();
        if (num.compareTo(inventory) < 0) {
            // 库存不足
            throw new RuntimeException("库存不足");
        }
        if (inventoryDomainService.changeInventory(skuId, inventory - num, num, 0L)) {
            BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
            BigDecimal totalAmount = calcTotalAmount(skuPrice, num);

            Order order = Order.builder()
                    .userId(userId)
                    .skuId(skuId)
                    .skuPrice(skuPrice)
                    .totalAmount(totalAmount)
                    .num(num).build();
            Order newOrder = orderDomainService.createOrder(order);
            // 传入订单信息和回调地址调用第三方支付接口, 获取支付链接并更新到订单记录上
            newOrder.setPayUrl("https://xxx");

            return newOrder;
        }
        throw new RuntimeException("库存预占失败");
    }

    @Override
    public void handleCallback(PayCallbackReq payCallbackReq) {
        payDomainService.payRecord();
        // 根据orderNo查询订单信息, 这边用new Order()代替
        Order order = new Order();
        if (checkOrderHandleComplete(order)) {
            return;
        }
        boolean paySuccess = Order.PayStatusEnum.PAY_SUCCESS.getCode().equals(payCallbackReq.getPayStatus());
        Long inventory = inventoryDomainService.getInventory(order.getSkuId());
        if (paySuccess) {
            order.setPayAmount(payCallbackReq.getPayAmount());
            order.setPayTime(payCallbackReq.getPayTime());
            order.setThirdPartySerialNumber(payCallbackReq.getSerialNumber());
            orderDomainService.orderPaySuccess(order);
            inventoryDomainService.changeInventory(order.getSkuId(), inventory, -order.getNum(), order.getNum());
        } else {
            orderDomainService.orderPayFail(order);
            inventoryDomainService.changeInventory(order.getSkuId(), inventory + order.getNum(), -order.getNum(), 0L);
        }
    }

    /**
     * @param order
     * @return true表示这笔订单的回调已经处理过
     */
    private boolean checkOrderHandleComplete(Order order) {
        return !Order.PayStatusEnum.WAIT_PAY.getCode().equals(order.getPayStatus());
    }


    private BigDecimal calcTotalAmount(BigDecimal price, Long num) {
        return price.multiply(BigDecimal.valueOf(num));
    }
}
