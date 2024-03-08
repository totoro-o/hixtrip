package com.hixtrip.sample.domain.order.repository;


import com.hixtrip.sample.client.common.ResultVo;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;

/**
 *
 */
public interface OrderRepository {
    ResultVo createOrder(CommandOderCreateDTO commandOderCreateDTO);

    ResultVo payCallback(CommandPayDTO commandPayDTO);
}
