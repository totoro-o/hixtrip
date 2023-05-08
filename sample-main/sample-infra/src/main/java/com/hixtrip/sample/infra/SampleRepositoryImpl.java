package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.sample.model.MallOrder;
import com.hixtrip.sample.domain.sample.model.OrderStatus;
import com.hixtrip.sample.domain.sample.model.Sample;
import com.hixtrip.sample.domain.sample.repository.SampleRepository;
import com.hixtrip.sample.infra.db.convertor.MallOrderConverter;
import com.hixtrip.sample.infra.db.convertor.SampleDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.MallOrderDO;
import com.hixtrip.sample.infra.db.dataobject.SampleDO;
import com.hixtrip.sample.infra.db.mapper.MallSkuOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class SampleRepositoryImpl implements SampleRepository {
    @Override
    public Sample test() {
        //此处可调用mybatis进行查询，DO转化为领域对象返回，如：
        return SampleDOConvertor.INSTANCE.doToDomain(SampleDO.builder().build());
    }

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

    @Override
    public MallOrder findOrderByOrderId(Long orderId) {
        MallOrderDO mallOrderDO = mallSkuOrderMapper.findOrderByOrderId(orderId);
        return mallOrderDO != null ? MallOrderConverter.INSTANCE.doToDomain(mallOrderDO) : null;
    }

    @Override
    public void updateOrderStatus(Long orderId, int status) {
        mallSkuOrderMapper.updateOrderStatus(orderId, status);
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return goodsDoMainService.getSkuPrice(skuId);
    }

    @Override
    public Boolean changeInventory(Long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        return inventoryService.changeInventory(skuId, sellableQuantity, withholdingQuantity, occupiedQuantity);
    }

    @Override
    public void payFail(Long orderId) {
        MallOrderDO mallOrderDO = mallSkuOrderMapper.findOrderByOrderId(orderId);
        if (mallOrderDO == null) {
            return;
        }
        if (mallOrderDO.getStatus() == OrderStatus.FAIL.getValue()) {
            return;
        }
        updateOrderStatus(orderId, OrderStatus.FAIL.getValue());
    }

    @Override
    public Long getInventory(Long skuId) {
        return goodsDoMainService.getSkuPrice(skuId).longValue();
    }

    @Override
    public void paySuccess(Long orderId) {
        MallOrderDO mallOrderDO = mallSkuOrderMapper.findOrderByOrderId(orderId);
        if (mallOrderDO == null) {
            return;
        }
        if (mallOrderDO.getStatus() == OrderStatus.SUCCESS.getValue()) {
            return;
        }
        payDomainService.payRecord();
        mallOrderDO.setStatus(OrderStatus.SUCCESS.getValue());
        updateOrderStatus(orderId, OrderStatus.SUCCESS.getValue());
    }
}
