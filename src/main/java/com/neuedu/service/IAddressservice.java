package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IAddressservice {
    //添加地址
    ServerResponse add(Integer userId, Shipping shipping);
    //删除地址
    ServerResponse del(Integer userId, Integer shippingId);
    //修改地址
    ServerResponse update(Shipping shipping);
    //  查看具体地址
    ServerResponse select(Integer shippingId);
    //  查看地址列表(分页)
    ServerResponse list(Integer pageNum, Integer pageSize);
}
