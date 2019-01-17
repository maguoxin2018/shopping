package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//后台管理员登录
@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {

    @Autowired
    IUserservice iUserservice;

    //    登录
    @RequestMapping(value = "/login.do")
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse login = iUserservice.login(username, password);
        if (login.isSuccess()){
            UserInfo date = (UserInfo)login.getDate();
            if (date.getRole() == Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                return ServerResponse.createServerResponseByFail("没有登录权限");
            }
            session.setAttribute(Const.CURRENTUSER,date);
        }
        return login;
    }
}
