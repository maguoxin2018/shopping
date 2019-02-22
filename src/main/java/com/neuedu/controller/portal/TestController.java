package com.neuedu.controller.portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.neuedu.Json.ObjectMapperApi;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.redis.RedisApi;
import com.neuedu.service.IUserservice;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class TestController {

    @Autowired
    JedisPool jedisPool;
    @RequestMapping(value = "/redis")
    public String getJedis(){
        Jedis resource = jedisPool.getResource();
        String set = resource.set("root23", "root23");
        return set;
    }
    @Autowired
    private RedisApi redisApi;
    @RequestMapping(value = "/key/{key}")
    public String getkey(@PathVariable("key") String key){
//        redisApi.set(key,"rootroot");
        String set = redisApi.get(key);
        return set;
    }

    @Autowired
    ObjectMapperApi objectMapperApi;
    @Autowired
    UserInfoMapper userInfoMapper;
    @RequestMapping(value = "/josn/{userid}")
    public ServerResponse<UserInfo> find(@PathVariable Integer userid){
        UserInfo userInfo=userInfoMapper.selectByPrimaryKey(userid);
        List<UserInfo> userInfoList= Lists.newArrayList();
        userInfoList.add(userInfo);
        String s = objectMapperApi.objTostr(userInfoList);
        List list =objectMapperApi.strToobj(s, new TypeReference<List<UserInfo>>() {});
        return ServerResponse.createServerResponseBySuccess(list,null);
    }



}
