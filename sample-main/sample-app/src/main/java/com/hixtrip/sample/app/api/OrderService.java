package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.OrderReq;
import com.hixtrip.sample.client.OrderVO;
import com.hixtrip.sample.client.PayCallback;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
public interface OrderService {
  OrderVO create(OrderReq req);

  void payCallback(PayCallback payCallback);
}
