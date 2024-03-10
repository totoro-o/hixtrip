package com.hixtrip.sample.infra.conf;

public class RedisConf {

    public static final String INVENTORY_KEY = "inventory:";

    public static final String INVENTORY_SELLABLE_QUANTITY = INVENTORY_KEY + "sellableQuantity";

    public static final String INVENTORY_WITHHOLDING_QUANTITY = INVENTORY_KEY + "withholdingQuantity";

    public static final String INVENTORY_OCCUPIED_QUANTITY = INVENTORY_KEY + "occupiedQuantity";
}
