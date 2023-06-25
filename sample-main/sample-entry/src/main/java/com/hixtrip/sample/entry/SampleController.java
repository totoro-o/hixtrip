package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.client.sample.dto.SampleReq;
import com.hixtrip.sample.client.sample.vo.SampleVO;
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

}
