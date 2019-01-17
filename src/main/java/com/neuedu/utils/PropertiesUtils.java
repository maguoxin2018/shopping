package com.neuedu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//加载配置文件的工具包
public class PropertiesUtils {

    private static Properties properties = new Properties();

    static {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        读取配置文件的内容
    public static String readBykey(String key){
        return properties.getProperty(key);
    }

}
