package com.hixtrip.sample.infra.api.impl;

import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.infra.db.dataobject.MallStockDO;
import com.hixtrip.sample.infra.db.mapper.MallStockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryServiceImpl implements InventoryDomainService {

    private MallStockMapper mallStockMapper;

    @Autowired
    public void setMallStockMapper(MallStockMapper mallStockMapper) {
        this.mallStockMapper = mallStockMapper;
    }

    @Override
    public long getInventory(long skuId) {
        MallStockDO mallStockDO = mallStockMapper.findOrderBySkuId(skuId);
        if (mallStockDO == null) {
            return 0;
        }
        return mallStockDO.getQuantity();
    }

    @Override
    public Boolean changeInventory(long skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity) {
        MallStockDO mallStockDO = mallStockMapper.findOrderBySkuId(skuId);
        if (mallStockDO == null) {
            return false;
        }
        return mallStockMapper.updateStockBySkuId(sellableQuantity, skuId) > 0;
    }
}
