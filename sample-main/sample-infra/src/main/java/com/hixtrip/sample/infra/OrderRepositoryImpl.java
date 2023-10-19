package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Component;

/**
 * <p> 订单存储实现类
 *
 * @author airness
 * @since 2023/10/18 20:06
 */
@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public void insert(Order order) {

    }

    @Override
    public void update(Order order) {

    }

    @Override
    public Order findById(String id) {
        return null;
    }
}
