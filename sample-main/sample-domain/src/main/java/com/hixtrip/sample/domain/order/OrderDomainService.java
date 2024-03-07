package com.hixtrip.sample.domain.order;

import com.hixtrip.sample.common.enums.OrderPayStatusEnum;
import com.hixtrip.sample.common.exception.BizException;
import com.hixtrip.sample.common.util.JsonData;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 订单领域服务
 * todo 只需要实现创建订单即可
 */
@Component
public class OrderDomainService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommodityDomainService commodityDomainService;

    @Autowired
    private InventoryDomainService inventoryDomainService;


    /**
     * todo 需要实现
     * 创建待付款订单
     */
    public void createOrder(Order order) {
        //需要你在infra实现, 自行定义出入参

        //验证库存
        Long inventory = inventoryDomainService.getInventory(order.getSkuId());
        if (inventory < order.getAmount()){
            throw new BizException(-1,"库存不足!");
        }

        //价格计算
        BigDecimal realPayAmount = this.priceCalculate(order.getSkuId(),order.getAmount());
        order.setMoney(realPayAmount);
        order.setPayStatus(OrderPayStatusEnum.NEW.name());

        //库存修改
        Boolean rs = inventoryDomainService.changeInventory(order.getSkuId(),
                inventory,
                Long.valueOf(order.getAmount()),
                Long.valueOf(order.getAmount()));

        if (!rs){
            throw new BizException(-1,"修改库存失败！");
        }

        //订单入库
        orderRepository.save(order);

    }

    /**
     * todo 需要实现
     * 待付款订单支付成功
     */
    public void orderPaySuccess(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.updateOrder(commandPay,OrderPayStatusEnum.PAY.name());
    }

    /**
     * todo 需要实现
     * 待付款订单支付失败
     */
    public void orderPayFail(CommandPay commandPay) {
        //需要你在infra实现, 自行定义出入参
        orderRepository.updateOrder(commandPay,OrderPayStatusEnum.CANCEL.name());
    }

    /**
     * 价格计算
     * @param skuId
     * @param amount
     * @return
     */
    public BigDecimal priceCalculate(String skuId, Integer amount) {
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);
        return skuPrice.multiply(new BigDecimal(amount));
    }
}
