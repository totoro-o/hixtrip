package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.R;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.infra.LockRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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
    private CommodityDomainService commodityDomainService;
    @Autowired
    private LockRepositoryImpl lockRepository;

    @Override
    public R createOrder(CommandOderCreateDTO req) {
        //获取当前库存，提前做一次库存判断，可以拦截排队中的并发后面再进来的请求
        Inventory inventory = inventoryDomainService.getInventory(req.getSkuId());
        if (inventory.getSellableQuantity() < req.getAmount()){
            return R.error(601,"库存不足");
        }
        //修改库存
        if (!this.lockInventory(req)){
            return R.error(601,"库存不足");
        }
        //查询单价
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(req.getSkuId());
        //创建订单
        return R.ok().put("order",orderDomainService.createOrder(req.getUserId(),req.getSkuId(),req.getAmount(),skuPrice));
    }


    // 其实我觉得把锁操作丢到修改库存的服务领域里面更合适，但是接口入参定死了，不好操作了0 0
    // 这个自己简单的实现了一个锁，避免超卖，实际业务场景应该考虑更多关于释放锁和锁续命的机制。用redission会比较好做
    private boolean lockInventory(CommandOderCreateDTO req){
        String skuId = req.getSkuId();
        try {
            //加锁
            lockRepository.getLock(skuId);
            //获取当前库存
            Inventory inventory = inventoryDomainService.getInventory(skuId);
            if (inventory.getSellableQuantity() < req.getAmount()){
                return false;
            }
            inventory.lockInventory(inventory.getSellableQuantity(),inventory.getWithholdingQuantity(),req.getAmount().longValue());
            if (inventoryDomainService.changeInventory(skuId,inventory.getSellableQuantity(),inventory.getWithholdingQuantity(),inventory.getOccupiedQuantity())){
                return true;
            }else {
                return false;
            }
        }finally {
            lockRepository.delLock(skuId);
        }
    }
}
