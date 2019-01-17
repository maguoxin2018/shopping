package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//类别管理的
@RestController
@RequestMapping(value = "/manage/category")
public class CategoryManageController {

    @Autowired
    ICategoryservice iCategoryservice;



    //    1.获取品类子节点(平级)
    @RequestMapping(value = "/get_category.do")
    public ServerResponse get_category(HttpSession session,Integer categoryId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return iCategoryservice.get_category(categoryId);
    }


    //增加节点（平级）
    @RequestMapping(value = "/add_category.do")
    public ServerResponse add_category(HttpSession session,
                                       @RequestParam(required = false,defaultValue = "0") Integer parentId,
                                       String categoryName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(),Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole() != Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return iCategoryservice.add_category(parentId,categoryName);
    }



    //修改节点
    @RequestMapping(value = "/set_category_name.do")
    public ServerResponse set_category_name(HttpSession session, Integer categoryId, String categoryName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(), Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() !=  Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return iCategoryservice.set_category_name(categoryId,categoryName);
    }


    //获取当前分类id及递归子节点categoryId
    @RequestMapping(value = "/get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session,
                                            Integer categoryId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NEED_LOGIN.getCode(), Const.ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断用户权限
        if (userInfo.getRole() !=  Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByFail(Const.ResponseCode.NO_PRIVILEGE.getCode(),Const.ResponseCode.NO_PRIVILEGE.getDesc());
        }
        return iCategoryservice.get_deep_category(categoryId);
    }

}
