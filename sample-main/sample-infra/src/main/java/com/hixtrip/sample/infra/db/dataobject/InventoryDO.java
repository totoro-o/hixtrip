package com.hixtrip.sample.infra.db.dataobject;

import lombok.Data;

/**
 * @CreateDate: 2023/10/24
 * @Author: ccj
 */
@Data
public class InventoryDO {

    private String id;

    private String name;
    // more info
    private Long sq;
    private Long wq;
    private Long oq;
}
