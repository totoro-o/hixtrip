package com.hixtrip.sample.infra;

import com.hixtrip.sample.client.common.ResultVo;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.payCallbackStrategy.PayCallBackStrategy;
import com.hixtrip.sample.infra.payCallbackStrategy.impl.PayCallBackStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * 创建还未支付的订单
     * @param commandOderCreateDTO
     * @return
     */
    @Override
    public ResultVo createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 1.检测库存是否足够
        Integer inventory = inventoryRepository.getInventory(commandOderCreateDTO.getSkuId());
        if(inventory < commandOderCreateDTO.getAmount()) {
            throw new RuntimeException("库存不足");
        }
        // 2.扣减库存
        Boolean changeSuccess = inventoryRepository.changeInventory(commandOderCreateDTO.getSkuId(), Long.valueOf(inventory), Long.valueOf(commandOderCreateDTO.getAmount()), Long.valueOf(1));
        if(!changeSuccess){
            return ResultVo.error("库存修改失败");
        }
        // 3.创建订单...还有其他的数据也得插入
        Order order = new Order();
        order.setUserId(commandOderCreateDTO.getUserId());
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setSkuId(commandOderCreateDTO.getSkuId());
        // 4.将这个订单存入数据库中

        return ResultVo.success(null);
    }

    @Override
    public ResultVo payCallback(CommandPayDTO commandPayDTO) {
        PayCallBackStrategyFactory.getInstance(commandPayDTO.getPayStatus()).handle(commandPayDTO.getOrderId());
        return ResultVo.success(null);
    }
}
