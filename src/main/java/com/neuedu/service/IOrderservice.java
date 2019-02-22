package com.neuedu.service;

import com.neuedu.common.ServerResponse;

import java.util.Map;

public interface IOrderservice {

    //  创建订单
    ServerResponse createOrder(Integer userId,Integer shippingId);
    //  取消订单
    ServerResponse cancel(Integer userId, Long orderNo);
    //  获取订单中的商品信息
    ServerResponse get_order_cart_product(Integer userId);
    //  订单列表
    ServerResponse list(Integer userId, Integer pageNum, Integer pageSize);
    //  订单详情
    ServerResponse detail(Long orderNo);
    //订单支付
    ServerResponse pay(Integer userId,Long orderNo);
    //支付宝回调
    ServerResponse callback(Map<String,String> map);
    //查看订单支付状态
    ServerResponse query_order_pay_status(Long orderNo);
    //根据创建时间查询订单
    void closeOrder (String time);
}
