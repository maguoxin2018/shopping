package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Service;


public interface ICartservice {
    //添加购物车信息
    ServerResponse add(Integer userId,Integer productId, Integer count);
    //购物车列表
    ServerResponse list(Integer userId);
    //更新购物车某商品的数量
    ServerResponse update(Integer userId, Integer productId, Integer count);
    //移除购物车中某些商品
    ServerResponse delete_product(Integer userId, String productIds);
    //购物车中选中某个商品
    ServerResponse select(Integer userId, Integer productId,Integer check);
    //查询购物车中商品数量
    ServerResponse get_cart_product_count(Integer userId);
}
