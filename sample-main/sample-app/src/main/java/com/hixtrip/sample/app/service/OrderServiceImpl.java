package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.callback.PaymentCallbackService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService domainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private PaymentCallbackService paymentCallbackService;


    /**
     * 创建订单
     * @param commandOderCreateDTO 订单参数dto
     * @return 返回值
     */
    Boolean createOrder(CommandOderCreateDTO commandOderCreateDTO){
        Order order = new Order();
        //属性赋值
        order.setId(UUID.randomUUID());
        order.setUserId(commandOderCreateDTO.getUserId());
        order.setAmount(commandOderCreateDTO.getAmount());
        order.setSkuId(commandOderCreateDTO.getSkuId());

        //将获取价格和计算总金额的逻辑封装到领域服务中  （未实现直接调用）
        BigDecimal totalPrice = domainService.calculateTotalPrice(
                commandOderCreateDTO.getSkuId(),
                commandOderCreateDTO.getAmount()
        );
        order.setMoney(totalPrice);
        domainService.createOrder(order);
        return  true;
    };

    /**
     * 支付回调处理
     * @param commandPayDTO 回调参数dto
     */
    void payCallback(CommandPayDTO commandPayDTO){
        CommandPay commandPay = OrderConvertor.INSTANCE.PayDtoToPay(commandPayDTO);
        paymentCallbackService.processCallback(commandPay.getPayStatus(),commandPay);
    };
}
