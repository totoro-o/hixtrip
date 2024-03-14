package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.status.PayStatus;
import com.hixtrip.sample.infra.exception.BusinessException;
import com.hixtrip.sample.infra.handler.IPayStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private IPayStrategyService payStrategyService;

    @Override
    public void createOrder(Order order) throws NoSuchAlgorithmException, IllegalAccessException {
       String type = "wechat";
       CommandPay commandPay = CommandPay.builder().build();
        switch (type) {
            case "wechat":
              payStrategyService.createOrder(order);
            default:
                break;
        }

        commandPay = payStrategyService.receiveCallback(order);

        //todo 状态落数据库


    }

    //推送回app层
    @Override
    public void orderPaySuccess(CommandPay commandPay) {

    }

    //推送回app层
    @Override
    public void orderPayFail(CommandPay commandPay) {

    }


    private Integer getRepeatCacheTimeout(){
        return  30 + new Random().nextInt(10);
    }
}
