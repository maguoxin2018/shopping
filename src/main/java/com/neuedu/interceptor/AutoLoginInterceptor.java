package com.neuedu.interceptor;

import com.google.gson.Gson;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.service.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {
    @Autowired
    IUserservice iUserservice;
    //是在请求之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("==============是在请求前===============");
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        if (cookies!=null&&cookies.length>0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("username")){
                    ServerResponse serverResponse = iUserservice.findInfoByToken(cookie.getValue());
                    if (serverResponse.isSuccess()){
                        request.getSession().setAttribute(Const.CURRENTUSER,serverResponse.getDate());
                        return true;
                    }
                }
            }
        }
        response.reset();
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerResponse serverResponse = ServerResponse.createServerResponseByFail(100,"需要登录");
        Gson gson = new Gson();
        String s = gson.toJson(serverResponse);
        writer.write(s);
        writer.flush();
        writer.close();
        return false;
    }
//    是在相应之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        System.out.println("=====================================================");
    }
//    是在整个请求结束后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
