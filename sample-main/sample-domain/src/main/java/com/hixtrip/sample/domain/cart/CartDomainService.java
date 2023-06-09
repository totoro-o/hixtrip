package com.hixtrip.sample.domain.cart;

import com.hixtrip.sample.domain.cart.model.Cart;
import com.hixtrip.sample.domain.cart.repository.CartRepository;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.commodity.model.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartDomainService {


    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CommodityDomainService commodityDomainService;

    /**
     * 获取购物车数据 包含商品信息
     *
     * @param cartIdList 购物车ID
     * @return 购物车数据
     */
    public List<Cart> findListByIdListAndCheckSku(List<Long> cartIdList) {
        List<Cart> cartList = cartRepository.listByCartIdList(cartIdList);
        List<Commodity> commodityList = commodityDomainService.listBySkuIdList(cartList.stream().map(Cart::getSkuId).collect(Collectors.toList()));
        cartList.forEach(cart -> {
            Commodity commodity = commodityList.stream().filter(s -> s.getSkuId().equals(cart.getSkuId())).findFirst().orElse(null);
            if (commodity == null) {
                throw new RuntimeException("购物车数据发生变化，请刷新");
            }
            cart.setPrice(commodity.getPrice());
            //.... 合并商品数据
        });
        return cartList;
    }

    public void delByIds(List<Long> cartIdList){

    }



}


