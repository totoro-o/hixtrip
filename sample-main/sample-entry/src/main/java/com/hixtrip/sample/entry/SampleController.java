package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.SampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
    @Autowired
    private SampleService sampleService;

    /**
     * 这是一个示例，即编码规范
     */
    @PostMapping(path = "/query/sample/test")
    public SampleVO test(@RequestBody SampleReq req) {
        return sampleService.test(req);
    }

    /**
     * todo 这是你要实现的接口
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/create")
    public String order(@RequestBody String s) {
        //登录信息可以在这里模拟
        var userId = "";
        return "";
    }

    /**
     * todo 这是模拟订单支付回调
     * @param s 请修改入参对象
     * @return 请修改出参对象
     */
    @PostMapping(path = "/command/order/pay/callback")
    public String payCallback(@RequestBody String s) {
        return "";
    }

}
