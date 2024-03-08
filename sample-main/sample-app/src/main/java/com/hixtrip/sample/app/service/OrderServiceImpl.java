package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.domain.component.PayFactory;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.common.util.JsonData;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDomainService orderDomainService;

    @Autowired
    private PayDomainService payDomainService;

    @Autowired
    private PayFactory payFactory;


    @Override
    public JsonData order(CommandOderCreateDTO commandOderCreateDTO) {
        /**
         *   此处可以做Token防重提交，避免用户同时有多次下单请求，大致代码逻辑如下：
         *    String orderToken = commandOderCreateDTO.getToken();
         *    if (StringUtils.isBlank(orderToken)){
         *       throw new BizException(BizCodeEnum.ORDER_CONFIRM_TOKEN_NOT_EXIST);
         *    }
         *    //原子操作 校验令牌，删除令牌
         *    String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
         *    Long rs = redisTemplate.execute(new DefaultRedisScript<>(script,Long.class),
         *         Arrays.asList(String.format(CacheKey.SUBMIT_ORDER_TOKEN_KEY,userId)),
         *         rderToken);
         *    if (rs  == 0L){
         *        throw new BizException(BizCodeEnum.ORDER_CONFIRM_TOKEN_EQUAL_FAIL);
         *     }
         */

        Order order = OrderConvertor.INSTANCE.OrderCreateDtoToOrder(commandOderCreateDTO);
        orderDomainService.createOrder(order);

        //创建支付
        //String payResult = payFactory.pay(payInfoVO);
        String payResult = "";
        if (StringUtils.isNotBlank(payResult)){
            return JsonData.buildSuccess(payResult);
        }else {
            return JsonData.buildError("创建支付失败！");
        }
    }

    @Override
    public JsonData payCallback(CommandPayDTO commandPayDTO) {
        CommandPay commandPay = OrderConvertor.INSTANCE.PayDtoToPay(commandPayDTO);

        //记录支付回调结果
        payDomainService.payRecord(commandPay);

        //重复支付、成功、失败处理
        String rs = payFactory.getRsStr(commandPay);

        return JsonData.buildSuccess(rs);
    }


}
