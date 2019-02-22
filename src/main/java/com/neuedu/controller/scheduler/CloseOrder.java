package com.neuedu.controller.scheduler;

import com.neuedu.redis.RedisProperties;
import com.neuedu.service.IOrderservice;
import com.neuedu.utils.PropertiesUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CloseOrder {
    @Autowired
    IOrderservice iOrderservice;

    @Scheduled(cron = "*/1 * * * * *")
    public void closeOrder(){
//        System.out.println("=======huandasjdniaklsda====");
        Integer closeOrderTime = Integer.parseInt(PropertiesUtils.readBykey("close.order.time"));
        String s = com.neuedu.utils.DateUtils.dateToString(DateUtils.addHours(new Date(), -closeOrderTime));
        iOrderservice.closeOrder(s);
    }
}
