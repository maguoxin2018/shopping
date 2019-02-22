package com.neuedu.Json;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class  ObjectMapperApi {
    @Autowired
    ObjectMapper objectMapper;
    /*
    * java对象转字符串
    * */
    public <T> String objTostr(T t){
        if (t == null){
            return null;
        }
        try {
            return t instanceof String ?(String)t: objectMapper.writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * java对象转字符串
     * */
    public <T> String objTostrPretty(T t){
        if (t == null){
            return null;
        }

        try {
            return t instanceof String ?(String)t: objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    /*
     * 将字符串转成java对象
     * */
    public <T> T strToobj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str)||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 将字符串转成java集合
     * */
    public <T>T strToobj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str)||typeReference==null){
            return null;
        }
        try {
            if (typeReference.getType().equals(String.class)){
                return (T)str;
            }
            return objectMapper.readValue(str,typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
