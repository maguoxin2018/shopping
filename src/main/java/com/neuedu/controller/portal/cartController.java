package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value ="/cart" )
public class cartController {
    @Autowired
    ICartservice ICartservice;

    //  购物车中添加商品
    @RequestMapping(value = "/add.do/{productId}/{count}")
    public ServerResponse add(HttpSession session, @PathVariable Integer productId,@PathVariable Integer count) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.add(userInfo.getId(), productId, count);
    }

    //  购物车列表
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.list(userInfo.getId());

    }


    //  更新购物车中某商品的数量
    @RequestMapping(value = "/update.do/{productId}/{count}")
    public ServerResponse update(HttpSession session,@PathVariable Integer productId,@PathVariable Integer count) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.update(userInfo.getId(),productId,count);
    }


    //  移除购物车中某商品
    @RequestMapping(value = "/delete_product.do/{productIds}")
    public ServerResponse delete_product(HttpSession session,@PathVariable String productIds) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.delete_product(userInfo.getId(),productIds);
    }


    //  购物车中选中某个商品
    @RequestMapping(value = "/select.do/{productId}")
    public ServerResponse select(HttpSession session,@PathVariable Integer productId) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.select(userInfo.getId(),productId,Const.CartCheckEnum.PRODUCT_CHECKED.getCode());
    }


    //  购物车中取消选中某个商品
    @RequestMapping(value = "/un_select.do/{productId}")
    public ServerResponse un_select(HttpSession session,@PathVariable Integer productId) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.select(userInfo.getId(),productId,Const.CartCheckEnum.PRODUCT_UNCHECKED.getCode());
    }


    //  购物车全选
    @RequestMapping(value = "/select_all.do")
    public ServerResponse select_all(HttpSession session) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.select(userInfo.getId(),null,Const.CartCheckEnum.PRODUCT_CHECKED.getCode());
    }

    //  购物车取消全选
    @RequestMapping(value = "/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.select(userInfo.getId(),null,Const.CartCheckEnum.PRODUCT_UNCHECKED.getCode());
    }


    //  购物车中商品数量
    @RequestMapping(value = "/get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session) {
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return ICartservice.get_cart_product_count(userInfo.getId());
    }
}