package com.hixtrip.sample.domain.order.repository;


import com.hixtrip.sample.domain.order.model.Cart;
import com.hixtrip.sample.domain.order.model.Order;

import java.util.List;

public interface OrderRepository {
    Boolean createOrder(Order order);


    void dcount(List<Cart> carts);


    /***
     * 支付成功修改状态
     */
    int updateAfterPayStatus(String id);
}
