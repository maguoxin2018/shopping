package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

//响应前端高复用对象
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {
    private Integer status;
    private T date;
    private String msg;
/*
*
*/
    private ServerResponse() {
    }
    private ServerResponse(Integer status) {
        this.status = status;
    }
    private ServerResponse(Integer status,String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(Integer status, T date, String msg) {
        this.status = status;
        this.date = date;
        this.msg = msg;
    }
    /*
    *   成功且返回只有StatusCode
    */
    public static ServerResponse createServerResponseBySuccess(){
         return new ServerResponse(StatusCode.SUCCESS);
    }
    /*
     *   成功且返回有StatusCode，msg
     */
    public static ServerResponse createServerResponseBySuccess(String msg){
        return new ServerResponse(StatusCode.SUCCESS,msg);
    }
    /*
     *   成功且返回StatusCode,msg,date或者返回StatusCode，date
     */
    public static <T> ServerResponse createServerResponseBySuccess(T date,String msg){
        return new ServerResponse(StatusCode.SUCCESS,date,msg);
    }
    /*
     *   失败且返回只有StatusCode，默认状态码
     */
    public static ServerResponse createServerResponseByFail(){
        return new ServerResponse(StatusCode.FAIL);
    }
    /*
     *   失败且返回只有StatusCode()，自定义状态码
     */
    public static ServerResponse createServerResponseByFail(Integer statuscode){
        return new ServerResponse(statuscode);
    }
    /*
     *   失败且返回StatusCode，msg,默认状态码
     */
    public static ServerResponse createServerResponseByFail(String msg){
        return new ServerResponse(StatusCode.FAIL,msg);
    }
    /*
     *   失败且返回StatusCode，msg,自定义状态码
     */
    public static ServerResponse createServerResponseByFail(Integer statuscode,String msg){
        return new ServerResponse(statuscode,msg);
    }
    /*
     *   失败且返回StatusCode,msg,date或者返回StatusCode，date，默认状态码
     */
    public static <T> ServerResponse createServerResponseByFail(T date,String msg){
        return new ServerResponse(StatusCode.FAIL,date,msg);
    }
    /*
     *   失败且返回StatusCode,msg,date或者返回StatusCode，date，自定义状态码
     */
    public static <T> ServerResponse createServerResponseByFail(Integer statuscode,T date,String msg){
        return new ServerResponse(statuscode,date,msg);
    }
    /*
     *   判断是否返回成功
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status==StatusCode.SUCCESS;
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getDate() {
        return date;
    }

    public void setDate(T date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
