package com.neuedu.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
//    @Pointcut("execution(public * com.neuedu.service.Imp.IUserserviceImp.*(..))")
//    public void  pointcut(){}
//
//
//    @Before("pointcut()")
//    public void before(){
//        System.out.println("========================before=========================");
//    }
//    @After("pointcut()")
//    public void after(){
//        System.out.println("================after===================");
//    }
}
