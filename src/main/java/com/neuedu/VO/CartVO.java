package com.neuedu.VO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

//返回到前端的购物车实体类
public class CartVO implements Serializable {
    //购物信息集合
    private List<CartProductVO> cartProductVO;
    //是否全选
    private boolean isallchecked;
    // 总价格
    private BigDecimal allprice;

    public List<CartProductVO> getCartProductVO() {
        return cartProductVO;
    }

    public void setCartProductVO(List<CartProductVO> cartProductVO) {
        this.cartProductVO = cartProductVO;
    }

    public boolean isIsallchecked() {
        return isallchecked;
    }

    public void setIsallchecked(boolean isallchecked) {
        this.isallchecked = isallchecked;
    }

    public BigDecimal getAllprice() {
        return allprice;
    }

    public void setAllprice(BigDecimal allprice) {
        this.allprice = allprice;
    }
}
