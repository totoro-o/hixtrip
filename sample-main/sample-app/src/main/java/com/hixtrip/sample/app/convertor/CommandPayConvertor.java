package com.hixtrip.sample.app.convertor;

import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * <p> 支付回调转换器
 *
 * @author airness
 * @since 2023/10/18 21:16
 */
@Mapper
public interface CommandPayConvertor {

    CommandPayConvertor INSTANCE = Mappers.getMapper(CommandPayConvertor.class);

    CommandPay toDomainObject(CommandPayDTO dto);
}
