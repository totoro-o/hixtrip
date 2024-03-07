package com.hixtrip.sample.app;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yepx
 * @version 1.0
 * @date 2024/3/7 10:37
 */
@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;


    @Test
    public void saveOrderTest() {
        // 设定 skuId = "skuId123"
        CommandOderCreateDTO cmd = CommandOderCreateDTO.builder()
                .amount(1)
                .skuId("skuId123")
                .userId("张三")
                .build();
        orderService.saveOrder(cmd);
    }
}
