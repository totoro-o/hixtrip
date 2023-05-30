package com.hixtrip.sample.client;

import lombok.Builder;
import lombok.Data;

/**
 * hixtrip <br/>
 *
 * @author sushuaihao 2023/5/30
 * @since
 */
@Data
@Builder
public class PayCallback {
  private String status;
  private Long orderId;
  private String paymentNumber;
}
