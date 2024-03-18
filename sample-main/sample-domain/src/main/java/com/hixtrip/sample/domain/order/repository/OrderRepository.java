package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.dto.ApiResult;
import com.hixtrip.sample.domain.order.model.Order;

/**
 * 领域层定义接口，基础设施层实现具体查询方式，如查数据库、缓存、调用第三方SDK等
 */
public interface OrderRepository {

    ApiResult<?> createOrder(Order order);

}
