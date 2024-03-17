package com.hixtrip.sample.infra.constant;

/**
 * 库存相关常量类
 */
public interface InventoryConstants {

    /**
     * 分割
     */
    String REDIS_SEPARATOR = ":";

    /**
     * 可售库存
     */
    String SELLABLE_QUANTITY_KEY = "sellable_quantity";

    /**
     * 预占库存
     */
    String WITHHOLDING_QUANTITY_KEY = "withholding_quantity";

    /**
     * 占用库存
     */
    String OCCUPIED_QUANTITY_KEY = "occupied_quantity";

    /**
     * redis key 前缀
     */
    String PREFIX_KEY = "inventory:";


    /**
     * 修改 脚本
     */
    String CHANGE_INVENTORY_SCRIPT = """
            local sellable_quantity_inventory_key = KEYS[1]
            local withholding_quantity_inventory_key = KEYS[2]
            local occupied_quantity_inventory_key = KEYS[3]
                        
            local sellable_quantity_inventory = tonumber(redis.call('GET', sellable_quantity_inventory_key)) or 0
            local withholding_quantity_inventory = tonumber(redis.call('GET', withholding_quantity_inventory_key)) or 0
            local occupied_quantity_inventory = tonumber(redis.call('GET', occupied_quantity_inventory_key)) or 0
                        
            local sellable_quantity_delta = tonumber(ARGV[1]) or 0
            local withholding_quantity_delta = tonumber(ARGV[2]) or 0
            local occupied_quantity_delta = tonumber(ARGV[3]) or 0
                        
            if sellable_quantity_delta == 0 and withholding_quantity_delta == 0 and occupied_quantity_delta == 0 then
                return 0  -- 不做任何修改
            end
                        
            local new_withholding_quantity_inventory = withholding_quantity_inventory + withholding_quantity_delta
            local new_occupied_quantity_inventory = occupied_quantity_inventory + occupied_quantity_delta
                        
            if new_withholding_quantity_inventory + new_occupied_quantity_inventory > sellable_quantity_inventory then
                return -1  -- 库存不足，无法操作
            end
                        
            redis.call('INCRBY', sellable_quantity_inventory_key, sellable_quantity_delta)
            redis.call('INCRBY', withholding_quantity_inventory_key, withholding_quantity_delta)
            redis.call('INCRBY', occupied_quantity_inventory_key, occupied_quantity_delta)
            -- 操作成功
            return 1
            """;

}
