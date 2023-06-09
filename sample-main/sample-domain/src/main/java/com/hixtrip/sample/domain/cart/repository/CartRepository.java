package com.hixtrip.sample.domain.cart.repository;

import com.hixtrip.sample.domain.cart.model.Cart;

import java.util.List;

public interface CartRepository {

    List<Cart> listByCartIdList(List<Long> cartIdList);

}
