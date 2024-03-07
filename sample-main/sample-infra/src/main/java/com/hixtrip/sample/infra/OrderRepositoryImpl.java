package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.enums.PayStatusEnums;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
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

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void createOrder(Order order) {
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doToDomain(order);
        orderMapper.insert(orderDO);
    }

    @Override
    public String orderPaySuccess(CommandPay commandPay) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        Long amount = Long.valueOf(orderDO.getAmount());
        inventoryDomainService.changeInventory(orderDO.getSkuId(),0L,-amount,amount);
        orderDO.setPayStatus(PayStatusEnums.PAY_SUCCESS.getCode());
        orderMapper.updateById(orderDO);
        return "支付成功";
    }
    // 支付失败 可售数量+amount 预售库存-amount 占用库存 0
    @Override
    public String orderPayFail(CommandPay commandPay) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());
        Long amount = Long.valueOf(orderDO.getAmount());
        inventoryDomainService.changeInventory(orderDO.getSkuId(),amount,-amount,0L);
        orderDO.setPayStatus(PayStatusEnums.PAY_FAIL.getCode());
        orderMapper.updateById(orderDO);
        return "支付失败";
    }
}
