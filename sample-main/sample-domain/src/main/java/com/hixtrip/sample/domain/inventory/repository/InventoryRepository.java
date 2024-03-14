package com.hixtrip.sample.domain.inventory.repository;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public interface InventoryRepository {


    public Integer getInventory(String skuId);


    public Boolean changeInventory(String skuId, Long sellableQuantity, Long withholdingQuantity, Long occupiedQuantity);



}
