package com.hixtrip.sample.app.config;

import com.hixtrip.sample.client.PayCallbackRequest;

public interface PayCallbackStrategy {
    String handlePayCallback(PayCallbackRequest request);

}
