-- 占用库存扣除
local sku = KEYS[1]
local quantity = tonumber(ARGV[1])
-- 操作结果：【-1：未成功】【0：已操作】【1：完成业务】
local result = -1
local item = redis.call('get', sku)

if item == nil or quantity == 0 then
    return result
end
-- 反序列化
local inventoryItem = cjson.decode(item)
-- 判断操作是否是增加库存
if quantity > 0 then
    -- 增加库存
    inventoryItem.sellableQuantity = inventoryItem.sellableQuantity + quantity
    result = inventoryItem.sellableQuantity
else
    -- 这里quantity是负数，将其转为正数
    local deductionQuantity = -quantity
    -- 扣减库存
    -- 扣减库存从占用库存中扣除，同时扣减实际库存
    local availableInventory = inventoryItem.sellableQuantity - inventoryItem.occupiedQuantity - inventoryItem.withholdingQuantity
    local changedQuantity = availableInventory - deductionQuantity
    if changedQuantity >= 0 then
        -- 如果剩余库存可被扣减，则进行扣减
        inventoryItem.sellableQuantity = inventoryItem.sellableQuantity - deductionQuantity
        inventoryItem.occupiedQuantity = inventoryItem.occupiedQuantity - deductionQuantity
        result = inventoryItem.sellableQuantity
    end
end
redis.call(set, cjson.encode(inventoryItem))
return result

