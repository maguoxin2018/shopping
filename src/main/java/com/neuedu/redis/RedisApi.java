package com.neuedu.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisApi {
    @Autowired
    private JedisPool jedisPool;
    /**
     * set 方法
     * */
    public String set(String key,String value) {
        Jedis jedis = null;
        String result =null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
        }catch(Exception e){
            jedisPool.returnBrokenResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }
    /**
     * setex 方法
     * */
    public String setex(String key,int second,String value) {
        Jedis jedis = null;
        String result =null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setex(key, second, value);
        }catch(Exception e){
            jedisPool.returnBrokenResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }
    /**
     * get 方法
     * */
    public String get(String key) {
        Jedis jedis = null;
        String result =null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.get(key);
        }catch(Exception e){
            jedisPool.returnBrokenResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }
    /**
     * del 方法
     * */
    public Long del(String key) {
        Jedis jedis = null;
        Long result =null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.del(key);
        }catch(Exception e){
            jedisPool.returnBrokenResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }
    /**
     * get 方法
     * */
    public Long set(String key,int second) {
        Jedis jedis = null;
        Long result =null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key,second);
        }catch(Exception e){
            jedisPool.returnBrokenResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }
    /**
     * 清空缓存
     * */
    public String flusDB(){
        String resul = null;
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            resul = jedis.flushDB();
        }catch (Exception e){
            jedisPool.returnResource(jedis);
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return resul;
    }
}
