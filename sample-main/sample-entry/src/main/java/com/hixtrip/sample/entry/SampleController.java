package com.hixtrip.sample.entry;

import com.hixtrip.sample.app.api.SampleService;
import com.hixtrip.sample.client.SampleReq;
import com.hixtrip.sample.client.SampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这是一个示例
 */
@RestController
public class SampleController {
    @Autowired
    private SampleService sampleService;

    public SampleVO test(@RequestBody SampleReq req) {
        return sampleService.test(req);
    }
}
