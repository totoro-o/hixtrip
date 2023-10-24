package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    InventoryDomainService inventoryDomainService;

    /**
     * 创建待付款订单
     */
    @Override
    public void createOrder(Order order) {
        //计算库存，扣除
        Integer inventory = inventoryDomainService.changeInventory(order.getSkuId(),-order.getAmount(),"sellable");
        if(inventory<0){
            throw new RuntimeException("创建订单失败商品库存不足");
        }
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doToDomain(order);
        //假设0待付款，1已付款，-1支付失败
        order.setPayStatus("0");
        int insert = orderMapper.insert(orderDO);
        if(insert<1){
            throw new RuntimeException("创建订单失败");
        }
    }

    /**
     * 待付款订单支付成功
     */
    @Override
    public void orderPaySuccess(CommandPay commandPay) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        if(orderDO==null){
            throw new RuntimeException("订单不存在");
        }
        //重复支付判定
        if("1".equals(orderDO.getPayStatus())){
            throw new RuntimeException("订单已是支付成功状态");
        }
        //更新为已支付
        orderDO.setPayStatus("1");
        orderDO.setPayTime(LocalDateTime.now());
        int updateCount = orderMapper.updateById(orderDO);
        if(updateCount<1){
            throw new RuntimeException("订单支付成功状态更新失败");
        }
        //计算库存，扣除
        inventoryDomainService.changeInventory(orderDO.getSkuId(),-orderDO.getAmount(),"withholding");
        inventoryDomainService.changeInventory(orderDO.getSkuId(),-orderDO.getAmount(),"occupied");
    }


    /**
     * 待付款订单支付失败
     */
    @Override
    public void orderPayFail(CommandPay commandPay) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        if(orderDO==null){
            throw new RuntimeException("订单不存在");
        }
        if(!"0".equals(orderDO.getPayStatus())){
            throw new RuntimeException("订单不是未支付状态");
        }
        //更新为已支付失败
        orderDO.setPayStatus("-1");
        int updateCount = orderMapper.updateById(orderDO);
        if(updateCount<1){
            throw new RuntimeException("订单支付失败状态更新失败");
        }
        //恢复可售库存
        Integer inventory = inventoryDomainService.changeInventory(orderDO.getSkuId(),orderDO.getAmount(),"sellable");
    }
}
