package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.enums.PayStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
public class OrderRepositoryImplTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void saveOrderTest(){

        Order order = Order.builder()
                .userId("张三")
                .skuId("sku123")
                .payStatus(PayStatus.UNPAID.name())
                .payTime(LocalDateTime.now())
                .createBy("System")
                .updateBy("System")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .amount(1)
                .money(BigDecimal.ONE).build();
        boolean result = orderRepository.saveOrder(order);
        assert result;
    }
}