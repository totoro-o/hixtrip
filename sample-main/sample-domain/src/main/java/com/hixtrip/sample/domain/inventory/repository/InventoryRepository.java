package com.hixtrip.sample.domain.inventory.repository;

import com.hixtrip.sample.domain.inventory.Inventory;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public interface InventoryRepository {
  Inventory get(Long skuId);

  Inventory save(Inventory inventory);
}
