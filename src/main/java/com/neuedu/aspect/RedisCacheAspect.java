package com.neuedu.aspect;
import com.neuedu.Json.ObjectMapperApi;
import com.neuedu.common.ServerResponse;
import com.neuedu.redis.RedisApi;
import com.neuedu.utils.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/***
 *
 * redis缓存切面类
 * **/
@Component
@Aspect
public class RedisCacheAspect {

//    定义切入点
    @Pointcut("execution(* com.neuedu.service.Imp.IProductserviceImp.*(..))")
    public void pointcut(){}

    @Autowired
    RedisApi redisApi;
    @Autowired
    ObjectMapperApi objectMapperApi;
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint){
        Object o=null;
        try {
            //key:MD5(全类名+方法名+参数)
            StringBuffer keyBuffer = new StringBuffer();
            //获取全类名
            String className = joinPoint.getTarget().getClass().getName();
            keyBuffer.append(className);
            System.out.println("============className===="+className);
            //获取目标方法的方法名
            String methodName = joinPoint.getSignature().getName();
            if (methodName.equals("saveOrUpdate")||methodName.equals("set_sale_status")||methodName.equals("upload")){
                redisApi.flusDB();
                System.out.println("=============清空缓存===========");
            }
            keyBuffer.append(methodName);
            System.out.println("============methodName==="+methodName);
            //方法中的参数
            Object[] objects = joinPoint.getArgs();
            if (objects != null ){
                for (Object arg:objects){
                    System.out.println("=========arg=========="+arg);
                    keyBuffer.append(arg);
                }
            }
            //step1：先读缓存
            String key = MD5Utils.getMD5Code(keyBuffer.toString());
            String json = redisApi.get(key);
            if (json != null&&!json.equals("")){
                System.out.println("===================读取到缓存==========");
                return objectMapperApi.strToobj(json,ServerResponse.class);
            }
            //执行目标方法
            o = joinPoint.proceed();
            System.out.println("=============读取数据库========"+o);
            if (o != null){
                String jsoncache = objectMapperApi.objTostr(o);
                String set = redisApi.set(key,jsoncache);
                System.out.println(set);
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;
    }

}
