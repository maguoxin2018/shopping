package com.neuedu.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IAddressservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class IAddressserviceImp implements IAddressservice {
    @Autowired
    ShippingMapper shippingMapper;

//    添加地址
    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        if (shipping == null){
            return ServerResponse.createServerResponseByFail("添加地址为空",null);
        }
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);
        Map<String,Integer> map= Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySuccess(map,null);
    }


//    删除地址
    @Override
    public ServerResponse del(Integer userId, Integer shippingId) {
        if (shippingId == null) {
            return ServerResponse.createServerResponseByFail("参数错误");
        }
        int i = shippingMapper.deleteByuserIdAndshippingId(userId, shippingId);
        if (i > 0 ){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByFail("删除失败",null);
    }
//   修改地址
    @Override
    public ServerResponse update(Shipping shipping) {
        if (shipping == null){
            return ServerResponse.createServerResponseByFail("参数错误");
        }
        int i = shippingMapper.updateByselectivkey(shipping);
        if (i > 0){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByFail("修改失败",null);
    }


//  查看具体地址信息
    @Override
    public ServerResponse select(Integer shippingId) {
        if (shippingId == null) {
            return ServerResponse.createServerResponseByFail("参数错误");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(shippingId);
        return ServerResponse.createServerResponseBySuccess(shipping,null);
    }


//  查看地址列表(分页)
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippings = shippingMapper.selectAll();
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.createServerResponseBySuccess(pageInfo,null);
    }


}
