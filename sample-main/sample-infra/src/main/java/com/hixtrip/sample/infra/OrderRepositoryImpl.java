package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import com.hixtrip.sample.infra.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * infra层是domain定义的接口具体的实现
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisUtil redisUtil;

    private static final long min = 100000000000L;
    private static final long max = 999999999999L;

    /**
     * 创建待付款订单
     *
     * @param order
     * @return
     */
    @Override
    public ApiResult<?> createOrder(Order order) {

        //生成订单号
        long orderId = getOrderId();
        //保存订单信息


        //返回创建结果

        return ApiResult.success();
    }


    /**
     * 设定获取的是绝对唯一订单id
     * @return
     */
    public static long getOrderId() {
        Random random = new Random();
        return random.nextLong() % (max - min + 1) + min;
    }
}
