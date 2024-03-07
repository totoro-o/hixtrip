package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.domain.enums.PayStatusEnums;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private CommodityDomainService commodityDomainService;

    @Override
    public void createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        Long inventory = Long.valueOf(inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId()));
        if (inventory<commandOderCreateDTO.getAmount()){
            throw new RuntimeException("库存不足");
        }
        Boolean flag=inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId()
                ,Long.valueOf(commandOderCreateDTO.getAmount())
                , Long.valueOf(commandOderCreateDTO.getAmount()), 0L);
        // 最大重试3次 库存扣减成功
        // 库存扣减成功
        if (flag){
            Order order=new Order();
            order.setAmount(commandOderCreateDTO.getAmount());
            order.setSkuId(commandOderCreateDTO.getSkuId());
            order.setMoney(commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId()));
            // payStatus应加枚举，消除魔法值 偷懒
            order.setPayStatus(PayStatusEnums.UN_PAY.getCode());
            order.setUserId(commandOderCreateDTO.getUserId());
            orderDomainService.createOrder(order);
        }


    }


}
