package com.hixtrip.sample.app;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ThreadImpl implements Runnable {

    @Autowired
    private OrderService orderService;
    @Autowired
    private InventoryDomainService inventoryDomainServicel;
    @Override
    public void run() {
        //随机生成2个客户 和购买的sku及数量
        Random random = new Random();
        String userId = "001";
        String skuId = "sku001";
        if (random.nextInt(2) == 0){
            userId = "002";
        }
        if (random.nextInt(2) == 0){
            skuId = "sku002";
        }
        int min = 1;
        int max = 5;
        int amount = random.nextInt(max - min + 1) + min;
        CommandOderCreateDTO req = new CommandOderCreateDTO();
        req.setSkuId(skuId);
        req.setUserId(userId);
        req.setAmount(amount);
        orderService.createOrder(req);
        Inventory inventory = inventoryDomainServicel.getInventory(req.getSkuId());
        System.out.println("用户:" + userId
                + "购买sku:"
                + skuId + "->"
                + amount + "个。可用库存还剩"
                + inventory.getSellableQuantity() + "个,预占库存"
                + inventory.getWithholdingQuantity() + "个");
    }

}