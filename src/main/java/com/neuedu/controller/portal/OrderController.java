package com.neuedu.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    IOrderservice iOrderservice;



//  创建订单
    @RequestMapping(value = "/createOrder.do/{shippingId}")
    public ServerResponse createOrder(HttpSession session,@PathVariable Integer shippingId){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.createOrder(userInfo.getId(),shippingId);
    }


//  取消订单
    @RequestMapping(value = "/cancel.do/{orderNo}")
    public ServerResponse cancel(HttpSession session,@PathVariable Long orderNo){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.cancel(userInfo.getId(),orderNo);
    }

//  获取订单中的商品信息
    @RequestMapping(value = "/get_order_cart_product.do")
    public ServerResponse cancget_order_cart_productel(HttpSession session){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.get_order_cart_product(userInfo.getId());
    }


//  订单列表
    @RequestMapping(value = "/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.list(userInfo.getId(),pageNum,pageSize);
    }

//  订单详情
    @RequestMapping(value = "/detail.do/{orderNo}")
    public ServerResponse detail(HttpSession session,@PathVariable Long orderNo){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.detail(orderNo);
    }



//  订单支付
    @RequestMapping(value = "/pay.do/{orderNo}")
    public ServerResponse pay(HttpSession session,@PathVariable Long orderNo){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.pay(userInfo.getId(),orderNo);
    }

//    支付宝服务器回调应用服务器接口
    @RequestMapping(value = "/alipay_callback.do")
    public ServerResponse callback(HttpServletRequest request){
        System.out.println("====支付宝服务器回调应用服务器接口===");
        Map<String,String[]> param = request.getParameterMap();
        Map<String,String> requestparameter = Maps.newHashMap();
        Iterator<String> iterator = param.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String[] strArr = param.get(key);
            String value = "";
            for (int i =0;i<strArr.length;i++){
                value=(i==strArr.length-1)?value+strArr[i]:value+strArr[i]+",";
            }
            requestparameter.put(key,value);
        }
        //支付宝验签
        try {
            requestparameter.remove("sign_type");
            boolean b = AlipaySignature.rsaCheckV2(requestparameter, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!b){
                return ServerResponse.createServerResponseByFail("非法请求，验证不通过");
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //处理业务逻辑
        return iOrderservice.callback(requestparameter);
    }

    //  查看订单支付状态
    @RequestMapping(value = "/query_order_pay_status.do/{orderNo}")
    public ServerResponse query_order_pay_status(HttpSession session,@PathVariable Long orderNo){
        //        判断用户是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo == null) {
            return ServerResponse.createServerResponseByFail("需要登录");
        }
        return iOrderservice.query_order_pay_status(orderNo);
    }
}
