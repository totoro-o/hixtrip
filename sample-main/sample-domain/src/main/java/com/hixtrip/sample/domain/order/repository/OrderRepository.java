package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.Order;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public interface OrderRepository {
  Order get(Long id);
  Order save(Order order);
}
