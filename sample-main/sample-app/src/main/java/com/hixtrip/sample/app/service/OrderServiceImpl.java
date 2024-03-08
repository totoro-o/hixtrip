package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.api.PaymentCallback;
import com.hixtrip.sample.app.convertor.CommandOrderConvertor;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;
    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private OrderRepository orderRepository;



    /**
     * 创建订单
     *
     * @param commandOderCreateDTO
     * @param loginAccount
     * @return
     */
    @Override
    public Order order(CommandOderCreateDTO commandOderCreateDTO, String loginAccount) {
        String skuId = commandOderCreateDTO.getSkuId();
        //通过skuId 和数量查询库存 判断库存是否
        Integer inventory = inventoryDomainService.getInventory(skuId);
        // 检查库存
        if (commandOderCreateDTO.getAmount() > inventory) {
            throw new RuntimeException("库存不足");
        } else {
            // 更新库存
            inventoryDomainService.changeInventory(skuId, (long) 0 - commandOderCreateDTO.getAmount(), (long) commandOderCreateDTO.getAmount(), null ,commandOderCreateDTO.getAmount());
        }

        // 通过 skuId 查询商品价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(skuId);

        // 订单赋值
        Order order = OrderConvertor.INSTANCE.oderDTOToOrder(commandOderCreateDTO);
        order.totalPrice(skuPrice);//计算总价
        order.setCreateBy(loginAccount);
        // 初始值赋值 模拟
        LocalDateTime nowDate = LocalDateTime.now();
        order.setPayTime(nowDate);
        order.setSellerId("卖方ID");
        order.setPayStatus("待支付");
        order.setDelFlag(0L);
        order.setBuyerName("买家姓名");
        order.setComplaintFlag(0L);
        // 创建待支付订单 并生成订单ID
        String orderId = orderDomainService.createOrder(order);

        // 进行支付调用
        CommandPay commandPay = new CommandPay();
        commandPay.setOrderId(orderId);
        commandPay.setPayStatus("待支付");
        payDomainService.payRecord(commandPay);

        return order;
    }

    /**
     * 支付回调处理
     *
     * @param commandPayDTO
     */
    @Override
    public String payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CommandOrderConvertor.INSTANCE.CommandPayDTOToCommandPay(commandPayDTO);
        String payStatus = commandPay.getPayStatus();
        String orderId = commandPay.getOrderId();
        // 通过订单ID 查询订单信息
        Order order = orderRepository.qryOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("未查询到相关订单信息");
        }
        // 检查状态是否重复
        if (checkRepeat(order.getPayStatus(), payStatus)) {
            if ("已支付".equals(payStatus)) {
                // 调用成功策略
                PaymentCallback paymentCallback = new SuccessPaymentCallback();
                paymentCallback.handlePaymentResult(order, payStatus);

            } else if ("支付失败".equals(payStatus)) {
                // 调用失败策略
                PaymentCallback paymentCallback = new FailurePaymentCallback();
                paymentCallback.handlePaymentResult(order, payStatus);
            }
        } else {
            // 调用重复策略
            PaymentCallback paymentCallback = new RepeatPaymentCallback();
            paymentCallback.handlePaymentResult(order, payStatus);
        }


        return "success";
    }

    /**
     * 校验重复支付，
     *
     * @param qryPayStatus
     * @param payStatus
     * @return
     */
    private boolean checkRepeat(String qryPayStatus, String payStatus) {

        // 检查订单状态是否已经改变，避免重复更新
        if (qryPayStatus.equals(payStatus)) {
            return false;
        }
        return true;
    }

}
