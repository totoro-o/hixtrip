package com.hixtrip.sample.infra.constants;

public class InventoryConstants {

    /**
     * redis库存信息key前缀
     */
    public static final String INVENTORY_INFO_KEY_PREFIX = "inventory:info:";

    /**
     * 总库存
     */
    public static final String TOTAL_QUANTITY = "totalQuantity";

    /**
     * 可售库存
     */
    public static final String SELLABLE_QUANTITY = "sellableQuantity";

    /**
     * 预占库存
     */
    public static final String WITHHOLDING_QUANTITY = "withholdingQuantity";

    /**
     * 占用库存
     */
    public static final String OCCUPIED_QUANTITY = "occupiedQuantity";

}
