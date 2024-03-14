package com.hixtrip.sample.infra.handler;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.CommandPayDO;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;

public interface IPayStrategyService {

     void createOrder(Order order) throws IllegalAccessException;

     /**
      * 接受回调
      * @param orderDO
      * @return
      */
     CommandPay receiveCallback(Order orderDO) throws IllegalAccessException;

     /**
      * 绑定do与service
      */
     void bindPayService(Class<?> doClass, PaymentHandleService paymenthandleService);



}
