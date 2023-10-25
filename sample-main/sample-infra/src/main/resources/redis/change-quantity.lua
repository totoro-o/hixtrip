local diffSq = tonumber(ARGV[1])
local diffWq = tonumber(ARGV[2])
local diffOq = tonumber(ARGV[3])


local vals = redis.call("hmget", KEYS[1], "sq", "wq", "oq");
local sq = tonumber(vals[1])
local wq = tonumber(vals[2])
local oq = tonumber(vals[3])

if sq == nil then
    return -1
end
if wq == nil then
    return -2
end
if oq == nil then
    return -3
end


local res = 0
if diffSq ~= nil and diffSq ~= 0 then
    if sq + diffSq >= 0 then
        redis.call("hincrby", KEYS[1], "sq", diffSq)
        res = res + 1
    else
        return res
    end
end
if diffWq ~= nil and diffWq ~= 0 then
    if wq + diffWq >= 0 then
        redis.call("hincrby", KEYS[1], "wq", diffWq)
        res = res + 2
    else
        return res
    end
end
if diffOq ~= nil and diffOq ~= 0 then
    if oq + diffOq >= 0 then
        redis.call("hincrby", KEYS[1], "oq", diffOq)
        res = res + 4
    else
        return res
    end
end
return res