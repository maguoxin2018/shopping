package com.neuedu.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

//    加法运算
    public static BigDecimal add(double price1,Double price2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(price1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(price2);
        return  bigDecimal1.add(bigDecimal2);
    }
    //    减法运算
    public static BigDecimal sub(double price1,Double price2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(price1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(price2);
        return  bigDecimal1.subtract(bigDecimal2);
    }
    //    乘法运算
    public static BigDecimal mul(double price1,Double price2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(price1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(price2);
        return  bigDecimal1.multiply(bigDecimal2);
    }
    //    除法运算,保留两位小数，并且四舍五入
    public static BigDecimal div(double price1,Double price2){
        BigDecimal bigDecimal1 = BigDecimal.valueOf(price1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(price2);
        return  bigDecimal1.divide(bigDecimal2,2,BigDecimal.ROUND_HALF_UP);
    }
}
