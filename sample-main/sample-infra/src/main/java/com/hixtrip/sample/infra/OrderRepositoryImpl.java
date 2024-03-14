package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.status.PayStatus;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import com.hixtrip.sample.infra.db.mapper.CommandPayMapper;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.exception.BusinessException;
import com.hixtrip.sample.infra.handler.IPayStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class OrderRepositoryImpl implements OrderRepository {


    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CommandPayMapper commandPayMapper;
    @Autowired
    private IPayStrategyService payStrategyService;
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void createOrder(Order order) throws NoSuchAlgorithmException, IllegalAccessException {
       String type = "wechat";
       CommandPay commandPay = CommandPay.builder().build();
        switch (type) {
            case "wechat":
              payStrategyService.createOrder(order);
            default:
                break;
        }

        commandPay = payStrategyService.receiveCallback(order);
        //todo 存入状态数据
        OrderDO orderDO = OrderDOConvertor.INSTANCE.doToDomain(order);
        orderMapper.insert(orderDO);
        commandPayMapper.insert(commandPay);

    }

    @Override
    public void orderPaySuccess(CommandPay commandPay) {

        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());

        if (!commandPay.getPayStatus().equals(PayStatus.PAY_SUCCESS.getName())){
            return;
        }

        Long amount = Long.valueOf(orderDO.getAmount());
        inventoryDomainService.changeInventory(orderDO.getSkuId(), 0L, -amount, amount);
        orderDO.setPayStatus(PayStatus.PAY_SUCCESS.getName());
        orderMapper.updateById(orderDO);

    }

    @Override
    public void orderPayFail(CommandPay commandPay) {
        OrderDO orderDO = orderMapper.selectById(commandPay.getOrderId());

        if (!commandPay.getPayStatus().equals(PayStatus.PAY_FAILED.getName()) || !commandPay.getPayStatus().equals(PayStatus.PAY_REAPET.getName())){
            return;
        }

        Long amount = Long.valueOf(orderDO.getAmount());
        inventoryDomainService.changeInventory(orderDO.getSkuId(), 0L, -amount, amount);
        orderDO.setPayStatus(PayStatus.PAY_SUCCESS.getName());
        orderMapper.updateById(orderDO);
    }


    private Integer getRepeatCacheTimeout(){
        return  30 + new Random().nextInt(10);
    }
}
