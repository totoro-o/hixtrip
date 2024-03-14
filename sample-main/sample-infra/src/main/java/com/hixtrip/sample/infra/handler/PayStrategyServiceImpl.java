package com.hixtrip.sample.infra.handler;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.domain.pay.status.PayStatus;
import com.hixtrip.sample.infra.RedisUtil;
import com.hixtrip.sample.infra.db.dataobject.CommandPayDO;
import com.hixtrip.sample.infra.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class PayStrategyServiceImpl implements IPayStrategyService{

    public static final String repeatTransactionKey = "id:order:repeat_key";

    @Autowired
    private RedisUtil redisUtil;

    private Map<Class, PaymentHandleService> paymentHandleServiceMap = new HashMap<>();


    @Override
    public void createOrder(Order order) throws IllegalAccessException {

    }

    @Override
    public CommandPay receiveCallback(Order orderDO) throws IllegalAccessException {
        return null;
    }

    @Override
    public void bindPayService(Class<?> doClass, PaymentHandleService paymenthandleService) {

    }


    public CommandPay doProcess(Order order) throws IllegalAccessException, NoSuchAlgorithmException {
        PaymentHandleService paymentHandleService = paymentHandleServiceMap.get(order.getClass());

        //todo 重复下单是否需要 在创建订单的时候， 根据订单信息计算一个hash值，判断redis中是否
        // 有key,有则不允许重复提交，没有则生成一个key,设置 30s过期时间，然后创建订单

        CommandPay commandPay = CommandPay.builder().build();
        //验签
        if (!paymentHandleService.checkSign(order)) {
            throw new BusinessException(order, PayStatus.PAY_FAILED.getName(), "xxx");

        }

        String repeatId = generateToken(order);
        String orderCheckId = redisUtil.get(repeatTransactionKey);

        //初次下单
        if (StringUtils.isEmpty(orderCheckId)) {
            redisUtil.set(repeatTransactionKey, repeatId , getRepeatCacheTimeout(), TimeUnit.SECONDS);
            commandPay = CommandPay.builder().orderId(order.getId()).payStatus(PayStatus.PAY_SUCCESS.getName()).build();

        } else if (orderCheckId.equals(repeatId)) {
            //当前生成的token与redis跟本订单id相关的缓存相等时，则认为是重复支付
            commandPay = CommandPay.builder().orderId(order.getId()).payStatus(PayStatus.PAY_REAPET.getName()).build();
            return commandPay;
        } else {
            try{
                //上面判断完是否重复支付后， 再次再回调函数当中判断是否接收成功
                commandPay = receiveCallback(order);
            } catch (Exception ex) {
                throw new BusinessException(order, PayStatus.PAY_FAILED.getName(), "支付失败");
            }

        }
        return paymentHandleService.process(commandPay);
    }


    protected  String generateToken(Order order) throws IllegalAccessException, NoSuchAlgorithmException {
        String orderId = order.getId();
        if (StringUtils.isEmpty(orderId)) {
            throw new IllegalAccessException("xxxx");
        }

        byte[] id =  orderId.getBytes(StandardCharsets.UTF_8);
        byte[] now = Long.toString(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(id);
        md.update(now);
        return bytesToHex(md.digest());
    }

    protected String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();

        for (int i=0; i< bytes.length;i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    private Integer getRepeatCacheTimeout(){
        return  30 + new Random().nextInt(10);
    }
}
