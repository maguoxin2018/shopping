package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserservice;
import com.neuedu.service.Imp.IUserserviceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserservice iUserservice;

//    登录
    @RequestMapping(value = "/login.do")
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse login = iUserservice.login(username, password);
        if (login.isSuccess()){
            UserInfo date = (UserInfo)login.getDate();
            session.setAttribute(Const.CURRENTUSER,date);
        }
        return login;
    }
//     注册
    @RequestMapping(value = "/register.do")
    public ServerResponse register(HttpSession session,UserInfo userInfo){
        ServerResponse register = iUserservice.register(userInfo);
        return register;
    }


//      根据用户名查询密保问题
    @RequestMapping(value="/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        ServerResponse serverResponse = iUserservice.forget_get_question(username);
        return serverResponse;
    }

    //      提交问题答案
    @RequestMapping(value="/forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){
        ServerResponse serverResponse = iUserservice.forget_check_answer(username,question,answer);
        return serverResponse;
    }
    //    重置密码
    @RequestMapping(value="/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String passwordNew,String forgettoken){
        ServerResponse serverResponse = iUserservice.forget_reset_password(username,passwordNew,forgettoken);
        return serverResponse;
    }


    //    检查用户名和邮箱是否有效
    @RequestMapping(value="/check_valid.do")
    public ServerResponse check_valid(String str,String type){
        ServerResponse serverResponse = iUserservice.check_valid(str,type);
        return serverResponse;
    }


    //    获取登录用户的信息
    @RequestMapping(value="/get_user_info.do")
    public ServerResponse get_user_info(HttpSession session){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("用户未登录");
        }
        userInfo.setPassword("");
        userInfo.setQuestion("");
        userInfo.setAnswer("");
        userInfo.setRole(null);
        return ServerResponse.createServerResponseBySuccess(userInfo,null);
    }
    //    登录状态修改密码
    @RequestMapping(value="/reset_password.do")
    public ServerResponse reset_password (HttpSession session,String passwordOld,String passwordNew){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("用户未登录");
        }
        return iUserservice.reset_password(userInfo.getUsername(),passwordOld,passwordNew);
    }



//        登录状态更新个人信息
    @RequestMapping(value="/update_information.do")
    public ServerResponse update_information (HttpSession session,UserInfo user){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("用户未登录");
        }
        user.setId(userInfo.getId());
        ServerResponse serverResponse = iUserservice.update_information(user);
        if (serverResponse.isSuccess()){
//            更新session中的用户信息
            UserInfo userInfo1 = iUserservice.selectUserByUserid(userInfo.getId());
            session.setAttribute(Const.CURRENTUSER,userInfo1);
        }
        return serverResponse;
    }


    //    获取登录用户的详细信息
    @RequestMapping(value="/get_information.do")
    public ServerResponse get_information(HttpSession session){
        UserInfo userInfo =(UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null){
            return ServerResponse.createServerResponseByFail("用户未登录");
        }
        userInfo.setPassword("");
        return ServerResponse.createServerResponseBySuccess(userInfo,null);
    }
    //    退出登录
    @RequestMapping(value="/logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createServerResponseBySuccess();
    }
}
