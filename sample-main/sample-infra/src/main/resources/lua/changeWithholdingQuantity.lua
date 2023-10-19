-- 预占库存扣除
local sku = KEYS[1]
local quantity = tonumber(ARGV[1])
-- 操作结果：【-1：未成功】【0：已操作】【1：完成业务】
local item = redis.call('get', sku)

if item == nil or quantity == 0 then
    return -1
end
-- 反序列化
local inventoryItem = cjson.decode(item)
if quantity > 0 then
    -- 预下单
    local availableInventory = (inventoryItem.sellableQuantity - inventoryItem.occupiedQuantity - inventoryItem.withholdingQuantity) - quantity
    if availableInventory >= 0 then
        inventoryItem.withholdingQuantity = inventoryItem.withholdingQuantity + quantity
        redis.call('set', cjson.encode(inventoryItem))
        return 1
    end
else
    -- 支付失败，移除预占用的库存
    inventoryItem.withholdingQuantity = inventoryItem.withholdingQuantity - quantity
    redis.call('set', cjson.encode(inventoryItem))
    result = inventoryItem.withholdingQuantity
end
return result

