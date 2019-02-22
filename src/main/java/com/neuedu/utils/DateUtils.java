package com.neuedu.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    public static final String STANDARD_FORMAT="yyyy-MM-dd HH:mm:ss";
    //将date转化为字符串
    public static String dateToString(Date date,String formate){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formate);
    }
    public static String dateToString(Date date){
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
    //将字符串的时间转化为时间格式
    public static Date strToDate(String str){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }
    public static Date strToDate(String str,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        return dateTime.toDate();
    }
}
