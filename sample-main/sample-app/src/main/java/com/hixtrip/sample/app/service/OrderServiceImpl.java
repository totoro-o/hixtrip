package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.common.ResultVo;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private OrderRepository orderRepository;


    @Override
    public BigDecimal getSkuPrice(String skuId) {
        return commodityDomainService.getSkuPrice(skuId);
    }

    @Override
    public ResultVo createOrder(CommandOderCreateDTO commandOderCreateDTO) {
        return orderRepository.createOrder(commandOderCreateDTO);
    }

    @Override
    public ResultVo payCallback(CommandPayDTO commandPayDTO) {
        return orderRepository.payCallback(commandPayDTO);
    }
}
