package com.neuedu;

import com.neuedu.interceptor.AutoLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//注册拦截器
@SpringBootConfiguration
public class RegisterInterceptor implements WebMvcConfigurer {
    @Autowired
    AutoLoginInterceptor autoLoginInterceptor;
    public void addInterceptors(InterceptorRegistry registry){
        //自动登录
        List<String> excludeList = new ArrayList<>();
        //用户模块不用拦截的
        excludeList.add("/user/login.do/**");
        excludeList.add("/user/register.do");
        excludeList.add("/user/forget_get_question.do");
        excludeList.add("/user/forget_check_answer.do");
        excludeList.add("/user/forget_reset_password.do");
        excludeList.add("/user/check_valid.do");
        excludeList.add("/user/logout.do");
        //前台用户搜索商品不用拦截的
        excludeList.add("/product/**");
        excludeList.add("/order/**");
        excludeList.add("/shipping/**");
        excludeList.add("/manage/category/**");
        excludeList.add("/manage/product/**");
        excludeList.add("/manage/user/**");
        registry.addInterceptor(autoLoginInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
    }
}
