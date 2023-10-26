package com.hixtrip.sample.infra.db.repository;

import com.hixtrip.sample.infra.db.dataobject.InventoryDO;
import org.springframework.data.repository.CrudRepository;

public interface InventoryDORepository extends CrudRepository<InventoryDO, String> {
}
