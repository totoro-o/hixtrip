package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.CallbackService;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.CallbackConvertor;
import com.hixtrip.sample.client.R;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.LockRepositoryImpl;
import com.hixtrip.sample.infra.OrderRepositoryImpl;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.db.payment.CallbackFactory;
import com.hixtrip.sample.infra.db.payment.SignCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class CallbackServiceImpl implements CallbackService {

    @Autowired
    private OrderRepositoryImpl orderRepository;

    @Override
    public R payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = CallbackConvertor.INSTANCE.commandPayDTOToCommandPay(commandPayDTO);
        //校验签名
        if (!SignCheck.signCheck(commandPayDTO.getSign())){
            return R.error(601,"签名错误");
        }
        //查询订单是否存在
        Order order = orderRepository.getOrderById(commandPayDTO.getOrderId());
        if (order == null) {
            return R.error(602,"订单不存在");
        }else {
            //校验一些订单状态……
        }
        //变更订单状态,使用bean工厂，通过订单状态获取bean来操作不同的业务
        commandPay.setSkuId(order.getSkuId());
        commandPay.setAmount(order.getAmount().longValue());
        if (!CallbackFactory.getPayStatus(commandPayDTO.getPayStatus()).pay(commandPay)){
            return R.error(603,"修改订单状态失败");
        }
        return R.ok();
    }

}
