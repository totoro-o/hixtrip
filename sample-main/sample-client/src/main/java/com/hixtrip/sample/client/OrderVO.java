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
public class OrderVO {
  private Long orderId;
  private String code;
  private String msg;
}
