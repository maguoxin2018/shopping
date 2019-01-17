package com.neuedu.VO;

import com.neuedu.pojo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class CartorderItemVo {

    private List<OrderItemVO> orderItemVoList;
    private String imgHost;
    private BigDecimal totalPrice;

    public List<OrderItemVO> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItemVO> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImgHost() {
        return imgHost;
    }

    public void setImgHost(String imgHost) {
        this.imgHost = imgHost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
