package com.neuedu.common;

public class Const {
    public static final String CURRENTUSER="currentuser";
    public static final String TRADE_SUCCESS="TRADE_SUCCESS";
//   定义用户权限
    public enum ResponseCode{
        NEED_LOGIN(2,"需要登录"),
        NO_PRIVILEGE(3,"无操作权限")
        ;
        private Integer code;
        private String desc;
        private  ResponseCode(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }


//  定义登录身份状态
    public enum RoleEnum{
        ROLE_ADMIN(0,"管理员"),
        ROLE_CUSTOMER(1,"普通用户")
        ;
        private Integer code;
        private String desc;
        private  RoleEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //   商品状态
    public enum ProductStatusEnum{
        PRODUCT_ONLINE(1,"在售"),
        PRODUCT_OFFLINE(2,"下架"),
        PRODUCT_DELETE(3,"删除")
        ;
        private int code;
        private String dese;
        private ProductStatusEnum(int code,String dese){
            this.code = code;
            this.dese = dese;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDese() {
            return dese;
        }

        public void setDese(String dese) {
            this.dese = dese;
        }
    }
    //  定义登录身份状态
    public enum CartCheckEnum{
        PRODUCT_CHECKED(1,"已勾选"),
        PRODUCT_UNCHECKED(0,"未选中")
        ;
        private Integer code;
        private String desc;
        private  CartCheckEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //  定义订单状态
    public enum OrderCheckEnum{

        ORDER_CANCELED(0,"已取消"),
        ORDER_UN_PAY(10,"未付款"),
        ORDER_PAYED(20,"已付款"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCCESS(50,"交易成功"),
        ORDER_CLOSE(60,"交易关闭"),
        ;

        private Integer code;
        private String desc;
        private  OrderCheckEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static  OrderCheckEnum coedOf (Integer code){
            for (OrderCheckEnum orderCheckEnum: values()) {
                if (code == orderCheckEnum.getCode()){
                    return orderCheckEnum;
                }
            }
            return null;
        }
    }
    //  付款方式状态
    public enum PaymentEnum{

        ONLINE(1,"在线支付")

        ;

        private Integer code;
        private String desc;
        private  PaymentEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public static  PaymentEnum coedOf (Integer code){
            for (PaymentEnum paymentEnum: values()) {
                if (code == paymentEnum.getCode()){
                    return paymentEnum;
                }
            }
            return null;
        }
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
    //  支付方式
    public enum PaymentPlatformEnum{
        ALIPAY(1,"支付宝")
        ;
        private Integer code;
        private String desc;
        private  PaymentPlatformEnum(Integer code,String desc){
            this.code=code;
            this.desc=desc;
        }
        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
