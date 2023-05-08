package com.hixtrip.sample.infra.api.impl;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.sample.model.MallOrder;
import com.hixtrip.sample.domain.sample.model.OrderStatus;
import com.hixtrip.sample.infra.db.convertor.MallOrderConverter;
import com.hixtrip.sample.infra.db.dataobject.MallOrderDO;
import com.hixtrip.sample.infra.db.mapper.MallSkuOrderMapper;
import com.hixtrip.sample.infra.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class OrderImplService extends OrderDomainService {

    private InventoryDomainService inventoryService;

    private MallSkuOrderMapper mallSkuOrderMapper;

    private CommodityDomainService goodsDoMainService;

    private PayDomainService payDomainService;


    @Autowired
    public void setGoodsDoMainService(CommodityDomainService goodsDoMainService) {
        this.goodsDoMainService = goodsDoMainService;
    }

    @Autowired
    public void setPayDomainService(PayDomainService payDomainService) {
        this.payDomainService = payDomainService;
    }

    @Autowired
    public void setMallSkuOrderMapper(MallSkuOrderMapper mallSkuOrderMapper) {
        this.mallSkuOrderMapper = mallSkuOrderMapper;
    }

    @Autowired
    public void setInventoryService(InventoryDomainService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * 可以加一个redis 锁
     *
     * @param userId
     * @param skuId
     * @param quantity
     * @return
     */
    @Transactional(rollbackFor = Throwable.class)
    @Override
    public MallOrder create(long userId, long skuId, long quantity) {
        long currentInventory = inventoryService.getInventory(skuId);
        if (currentInventory <= 0 || currentInventory < quantity) {
            return MallOrder.builder().success(false)
                    .errorMsg("库存不足")
                    .build();
        }
//        可售库存= 库存量-预占库存
        boolean result = inventoryService.changeInventory(skuId, currentInventory - quantity, quantity, quantity);
        if (!result) {
            return MallOrder.builder().success(false)
                    .errorMsg("修改库存失败")
                    .build();
        }
        float price = goodsDoMainService.getSkuPrice(skuId).floatValue();
        //假设当前的机器id为100 实际为获取redis 的值
        SnowFlake snowFlake = new SnowFlake(100);
        MallOrderDO mallOrderDO = MallOrderDO.builder()
                .createTime(new Date())
                .orderId(snowFlake.nextId())
                .skuId(skuId)
                .quantity(quantity)
                .status(OrderStatus.WAIT_PAY.getValue())
                .userId(userId)
                .price(price)
                .remark("")
                .build();
        MallOrder mallOrder = MallOrderConverter.INSTANCE.doToDomain(mallOrderDO);
        mallOrder.setSuccess(true);
        return mallOrder;
    }


    @Override
    public void orderPaySuccess(Long orderId) {
        MallOrderDO mallOrderDO = mallSkuOrderMapper.findOrderByOrderId(orderId);
        if (mallOrderDO == null) {
            return;
        }
        if (mallOrderDO.getStatus() == OrderStatus.SUCCESS.getValue()) {
            return;
        }

        mallOrderDO.setStatus(OrderStatus.SUCCESS.getValue());
        mallSkuOrderMapper.saveOrUpdate(mallOrderDO);
        //记录成功的相关的日志
        payDomainService.payRecord();
    }

    @Override
    public void orderPayFail(Long orderId) {
        MallOrderDO mallOrderDO = mallSkuOrderMapper.findOrderByOrderId(orderId);
        if (mallOrderDO == null) {
            return;
        }
        if (mallOrderDO.getStatus() == OrderStatus.FAIL.getValue()) {
            return;
        }
        mallOrderDO.setStatus(OrderStatus.FAIL.getValue());
        mallSkuOrderMapper.saveOrUpdate(mallOrderDO);
    }
}
