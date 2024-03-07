package com.hixtrip.sample.app.api;

import com.hixtrip.sample.client.R;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 * 订单的service层
 */
public interface CallbackService {

    R payCallback(CommandPayDTO commandPayDTO);
}
