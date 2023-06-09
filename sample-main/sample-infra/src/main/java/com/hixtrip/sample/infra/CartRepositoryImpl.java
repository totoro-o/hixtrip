package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.cart.model.Cart;
import com.hixtrip.sample.domain.cart.repository.CartRepository;
import com.hixtrip.sample.infra.db.convertor.CartDOConvertor;
import com.hixtrip.sample.infra.db.dataobject.CartDO;
import com.hixtrip.sample.infra.db.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartRepositoryImpl implements CartRepository {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> listByCartIdList(List<Long> cartIdList) {
        List<CartDO> dbList = cartMapper.listByCartIdList();
        return dbList.stream().map(CartDOConvertor.INSTANCE::doToDomain).collect(Collectors.toList());
    }

}
