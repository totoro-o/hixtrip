package com.hixtrip.sample.infra.db.payment;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.LockRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class CallbackSuccessServiceImpl implements CallbackService {

    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private LockRepositoryImpl lockRepository;

    @Override
    public Boolean pay(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
        payDomainService.payRecord(commandPay);
        //释放预占库存
        if (!withholdingInventory(commandPay.getSkuId(),commandPay.getAmount())){
            return false;
        }
        System.out.println("支付成功");
        return true;
    }



    private boolean withholdingInventory(String skuId,Long amount){
        try {
            //加锁
            lockRepository.getLock(skuId);
            //获取当前库存
            Inventory inventory = inventoryDomainService.getInventory(skuId);
            //预占库存->占用库存
            inventory.occupyInventory(inventory.getWithholdingQuantity(),inventory.getOccupiedQuantity(),amount);
            if (inventoryDomainService.changeInventory(skuId,inventory.getSellableQuantity(),inventory.getWithholdingQuantity(),inventory.getOccupiedQuantity())){
                return true;
            }else {
                return false;
            }
        }finally {
            lockRepository.delLock(skuId);
        }
    }

    @Override
    public String getPayStatus() {
        return "success";
    }
}
