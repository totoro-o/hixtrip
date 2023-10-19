-- 占用库存扣除
local sku = KEYS[1]
local quantity = tonumber(ARGV[1])
-- 操作结果：【-1：未成功】【0：已操作】【1：完成业务】
local item = redis.call('get', sku)

if item == nil or quantity == 0 then
    return -1
end
-- 反序列化
local inventoryItem = cjson.decode(item)
-- 支付成功，将预占用库存移入实际占用库存
local availableInventory = inventoryItem.withholdingQuantity - quantity
if availableInventory >= 0 then
    -- 可进行操作
    inventoryItem.withholdingQuantity = availableInventory
    inventoryItem.occupiedQuantity = inventoryItem.occupiedQuantity + quantity
    redis.call(set, cjson.encode(inventoryItem))
    return 1
end

