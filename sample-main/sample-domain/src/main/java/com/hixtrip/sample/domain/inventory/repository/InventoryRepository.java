package com.hixtrip.sample.domain.inventory.repository;

import java.math.BigInteger;

/**
 *
 */
public interface InventoryRepository {

    BigInteger getInventory(String skuId);

    void changeWithholdingQuantity(String skuId, Long sellableQuantity, Long withholdingQuantity);

    void changeOccupiedQuantity(String skuId, Long occupiedQuantity);
}
