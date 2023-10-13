package com.hixtrip.sample.domain.order.constants;

public class InventoryConstants {

    /**
     * Redis存储库存key前缀
     */
    public static final String INVENTORY_REDIS_KEY_PREFIX = "order:inventory:skuid:";

    /**
     * redis存储库存 - 已销售
     */
    public static final String SELL = "sell";

    /**
     * redis存储库存 - 总库存
     */
    public static final String TOTAL = "total";
}
