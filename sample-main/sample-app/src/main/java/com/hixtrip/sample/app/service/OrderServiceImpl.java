package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Sku;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private PayDomainService payDomainService;



    @Override
    @Transactional
    public String placeOrder(CommandOderCreateDTO commandOderCreateDTO) {
        // 检查库存是否足够
        Integer availableInventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        if (availableInventory < commandOderCreateDTO.getAmount()) {
            throw new RuntimeException("库存不足");
        }
        Sku skuInfo = inventoryDomainService.getSkuInfo(commandOderCreateDTO.getSkuId());
        Order order = Order.builder()
                .id(UUID.randomUUID().toString()) // 生成订单号，可以使用UUID或者其他唯一标识符
                .userId(commandOderCreateDTO.getUserId()) // 设置购买人ID
                .skuId(commandOderCreateDTO.getSkuId()) // 设置商品ID
                .amount(commandOderCreateDTO.getAmount()) // 设置购买数量
                .money(skuInfo.getMoney())// 金额
                .payTime(LocalDateTime.now()) // 设置购买时间为当前时间
                .payStatus("待支付") // 设置支付状态为待支付
                .delFlag(0L) // 设置删除标志为0，表示存在
                .createBy("JAVA开发_李明康") // 设置创建人（模拟（真实业务根据userID查询））
                .createTime(LocalDateTime.now()) // 设置创建时间为当前时间
                .updateBy(null) // 修改人初始值为null
                .updateTime(null) // 修改时间初始值为null
                .build();
        int res = orderDomainService.createOrder(order);
        if (res > 0) {
            boolean inventoryChanged = inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), skuInfo.getSellableQuantity()-(long) commandOderCreateDTO.getAmount(), skuInfo.getWithholdingQuantity()+1, skuInfo.getOccupiedQuantity()+1);
            if (!inventoryChanged) {
                // 库存更改失败，可能发生并发问题等
                throw new RuntimeException("库存更改失败");
            }
            // 发起支付
            CommandPay commandPay = CommandPay.builder()
                    .orderId(order.getId())
                    .payStatus("已支付")
                    .build();
            try {
                // 调用支付服务完成支付记录
                payDomainService.payRecord(commandPay);
                orderDomainService.orderPaySuccess(commandPay);
                boolean inventoryChangedSucess = inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), skuInfo.getSellableQuantity(), skuInfo.getWithholdingQuantity()-1, skuInfo.getOccupiedQuantity()-1);
                if (!inventoryChangedSucess) {
                    // 库存更改失败，可能发生并发问题等
                    throw new RuntimeException("库存更改失败");
                }
                // 更改订单状态为已支付
                commandPay.setPayStatus("已支付");
                orderDomainService.orderPaySuccess(commandPay);
                return "订单创建成功";
            } catch (Exception e) {
                commandPay.setPayStatus("未支付");
                orderDomainService.orderPayFail(commandPay);
                return "订单创建失败";
            }
        } else {
            return "创建订单失败";
        }
    }
}
