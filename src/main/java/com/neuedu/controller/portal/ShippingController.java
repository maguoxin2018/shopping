package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IAddressservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {
    @Autowired
    IAddressservice iAddressservice;
//  添加地址
    @RequestMapping(value = "/add.do")
    public ServerResponse add(HttpSession session, Shipping shipping){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iAddressservice.add(userInfo.getId(),shipping);
    }

//  删除地址
    @RequestMapping(value = "/del.do/{shippingId}")
    public ServerResponse del(HttpSession session,@PathVariable Integer shippingId){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iAddressservice.del(userInfo.getId(),shippingId);
    }


//  修改地址
    @RequestMapping(value = "/update.do")
    public ServerResponse update(HttpSession session, Shipping shipping){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        shipping.setUserId(userInfo.getId());
        return iAddressservice.update(shipping);
    }


//  查看具体地址
    @RequestMapping(value = "/select.do/{shippingId}")
    public ServerResponse select(HttpSession session,@PathVariable Integer shippingId){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iAddressservice.select(shippingId);
    }

//  查看地址列表(分页)
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iAddressservice.list(pageNum,pageSize);
    }
}
