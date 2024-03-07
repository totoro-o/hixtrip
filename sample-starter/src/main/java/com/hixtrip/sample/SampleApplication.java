package com.hixtrip.sample;

import com.hixtrip.sample.app.ThreadImpl;
import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.service.CallbackServiceImpl;
import com.hixtrip.sample.app.service.OrderServiceImpl;
import com.hixtrip.sample.client.R;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.model.Inventory;
import com.hixtrip.sample.infra.InventoryRepositoryImpl;
import com.hixtrip.sample.infra.db.payment.CallbackFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@SpringBootApplication(scanBasePackages = {"com.hixtrip"})
@MapperScan(basePackages = {"com.hixtrip.sample.infra.db.mapper"})
public class SampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SampleApplication.class, args);
        //初始化一些sku数据到缓存
        init(run);
        //模拟用户并发下单 可注释
        createOrderByThread(run);
        //测试回调 可注释
        callback(run);
    }

    //支付回调测试方法，主要实现了支付成功的库存操作，可以修改 payStatus sign 参数观察其他返回结果
    public static void callback(ConfigurableApplicationContext run){
        CallbackServiceImpl callbackService = (CallbackServiceImpl)run.getBean("callbackServiceImpl");
        InventoryRepositoryImpl redisTemplate = (InventoryRepositoryImpl)run.getBean("inventoryRepositoryImpl");
        System.out.println("支付回调测试，起始sku001库存" + redisTemplate.getInventoryBySkuId("sku001"));
        //可以修改状态 success fail rePay 测试调用
        String payStatus = "success";
        //可修改 true false 测试调用
        String sign = "true";
        CommandPayDTO commandPayDTO = new CommandPayDTO();
        commandPayDTO.setOrderId("1");
        commandPayDTO.setPayStatus(payStatus);
        commandPayDTO.setSign(sign);
        R r = callbackService.payCallback(commandPayDTO);
        System.out.println(r);
        System.out.println("支付回调测试，最终sku001库存" + redisTemplate.getInventoryBySkuId("sku001"));
    }

    //初始化商品数据
    public static void init(ConfigurableApplicationContext run){
        InventoryRepositoryImpl redisTemplate = (InventoryRepositoryImpl)run.getBean("inventoryRepositoryImpl");
        Map<String,Object> map = new HashMap();
        Inventory inventory = new Inventory();
        inventory.setSkuId("sku001");
        inventory.setSellableQuantity(100l);
        inventory.setWithholdingQuantity(10l);
        inventory.setOccupiedQuantity(0l);
        map.put("sku001",inventory);

        Inventory inventory2 = new Inventory();
        inventory2.setSkuId("sku002");
        inventory2.setSellableQuantity(50l);
        inventory2.setWithholdingQuantity(0l);
        inventory2.setOccupiedQuantity(0l);
        map.put("sku002",inventory2);
        redisTemplate.init(map);

    }

    //模拟多线程并发下单
    public static void createOrderByThread(ConfigurableApplicationContext run){
        InventoryRepositoryImpl redisTemplate = (InventoryRepositoryImpl)run.getBean("inventoryRepositoryImpl");
        //用多线程模拟了一下并发下单操作，因为是并发操作，所以打印顺序会比较混乱，但是最终结果都是正确的
        System.out.println("起始sku001库存" + redisTemplate.getInventoryBySkuId("sku001"));
        System.out.println("起始sku002库存" + redisTemplate.getInventoryBySkuId("sku002"));
        Runnable t1 = (Runnable)run.getBean("threadImpl");
        for (int i = 0; i < 10; i++) {
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            new Thread(t1).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("最终sku001库存" + redisTemplate.getInventoryBySkuId("sku001"));
        System.out.println("最终sku002库存" + redisTemplate.getInventoryBySkuId("sku002"));
    }
}
