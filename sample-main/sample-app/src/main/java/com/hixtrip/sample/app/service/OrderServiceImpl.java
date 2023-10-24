package com.hixtrip.sample.app.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDomainService orderDomainService;

    @Autowired
    CommodityDomainService commodityDomainService;

    @Autowired
    PayDomainService payDomainService;
    /**
     * 创建订单
     * @param commandOderCreateDTO
     * @return
     */
    @Override
    public Order createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        if(commandOderCreateDTO.getAmount() ==null || commandOderCreateDTO.getAmount()<1){
            throw new RuntimeException("商品数量错误");
        }
        Order order = new Order();
        order.setId(IdWorker.getIdStr());
        order.setUserId(commandOderCreateDTO.getUserId());
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setSkuId(commandOderCreateDTO.getSkuId());
        //计算订单金额
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        BigDecimal totalPrice = skuPrice.multiply(new BigDecimal(commandOderCreateDTO.getAmount()));
        order.setMoney(totalPrice);
        orderDomainService.createOrder(order);
        return order;
    }

    /**
     * 支付回调处理
     * @param commandPayDTO
     */
    @Override
    public void payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = new CommandPay(commandPayDTO.getOrderId(),commandPayDTO.getPayStatus());
        //假设支付状态-1支付失败,0待付款，1已付款，2重复支付
        switch (commandPayDTO.getPayStatus()){
            case "-1":
                //失败
                orderDomainService.orderPayFail(commandPay);
                break;
            case "1":
                //成功
                orderDomainService.orderPaySuccess(commandPay);
                break;
            case "2":
                //回调是重复支付这种业务情况我没有遇到过，之前支付宝和微信如果是同一个订单发起付款，如果已经支付了是无法发起的，如果用的是第三方，大部分已经避免了重复发起支付的情况
                //这一块处理要根据第三方来，我这里描述一个简单的场景，如果重复支付了，并且客户扣了两次钱，这里要做一个给客户退款的操作
                break;
            default:
                throw new RuntimeException("订单支付状态不存在");
        }
        //记录支付回调结果
        payDomainService.payRecord(commandPay);
    }
}
