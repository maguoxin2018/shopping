package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product")
public class ProductManageController {

    @Autowired
    IProductservice iProductservice;

//      新增or更新商品商品
    @RequestMapping(value ="/save.do" )
    public ServerResponse addOrUpdate(HttpSession session,Product product){
//        判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
//        判断用户是否拥有管理员权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return  iProductservice.addOrUpdate(product) ;
    }




//      管理商品上下架
    @RequestMapping(value = "/set_sale_status.do/{productId}/{status}")
    public ServerResponse set_sale_status(HttpSession session, @PathVariable Integer productId,@PathVariable Integer status){
    //        判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
    //        判断用户是否拥有管理员权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return  iProductservice.set_sale_status(productId,status);
    }



//      商品详情
    @RequestMapping(value = "/detail.do/{productId}")
    public ServerResponse detail(HttpSession session,Integer productId){
    //        判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
    //        判断用户是否拥有管理员权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return  iProductservice.detail(productId);
    }




//      查看商品列表（分页）
    @RequestMapping(value = "/list.do/")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        //        判断用户是否拥有管理员权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return  iProductservice.list(pageNum,pageSize);
    }




//      搜索商品（分页）
    @RequestMapping(value = "/search.do")
    public ServerResponse search(HttpSession session,
                                 @RequestParam(value = "productId",required = false)Integer productId,
                                 @RequestParam(value = "productName",required = false)String productName,
                                 @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        //        判断用户是否拥有管理员权限
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return  iProductservice.search(productId,productName,pageNum,pageSize);
    }

}















































































































