package com.hixtrip.sample.app.api;

import com.hixtrip.sample.app.vo.OrderVo;
import com.hixtrip.sample.app.vo.PayCallBackVO;

import java.lang.reflect.InvocationTargetException;

public interface OrderService {
    /**
     * 创建订单
     * @param orderVo
     * @return
     * @throws Exception
     */
    boolean createOrder(OrderVo orderVo) throws Exception;

    /**
     * 支付回调
     * @param payCallBackVO
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    boolean payCallback(PayCallBackVO payCallBackVO) throws InvocationTargetException, IllegalAccessException;
}
