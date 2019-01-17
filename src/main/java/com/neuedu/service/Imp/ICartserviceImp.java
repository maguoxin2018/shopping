package com.neuedu.service.Imp;

import com.google.common.collect.Lists;
import com.neuedu.VO.CartProductVO;
import com.neuedu.VO.CartVO;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartservice;
import com.neuedu.utils.BigDecimalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ICartserviceImp implements ICartservice {
    @Autowired
    CartMapper cartMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        //参数校验
        if (productId == null || count == null){
            return ServerResponse.createServerResponseByFail("参数为空",null);
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createServerResponseByFail("要添加的商品不存在",null);
        }
        //  根据userid和productid  查询购物信息
        Cart cart = cartMapper.selectCarByuserIdAndproductId(userId, productId);
        if (cart == null){
            Cart cart1 = new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setChecked(Const.CartCheckEnum.PRODUCT_CHECKED.getCode());
            cart1.setQuantity(count);
            cartMapper.insert(cart1);
        }else {
            Cart cart1 = new Cart();
            cart1.setId(cart.getId());
            cart1.setProductId(productId);
            cart1.setUserId(userId);
            cart1.setQuantity(count);
            cart1.setChecked(cart.getChecked());
            cartMapper.updateByPrimaryKey(cart1);
        }
        CartVO cartVO = getcartVOlimit(userId);
        return ServerResponse.createServerResponseBySuccess(cartVO,null);
    }



//   购物车列表
    @Override
    public ServerResponse list(Integer userId) {
        CartVO cartVO = getcartVOlimit(userId);
        return ServerResponse.createServerResponseBySuccess(cartVO,null);
    }




//  更新购物车中某商品的数量
    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        //参数判断
        if (productId == null || count == null){
            return ServerResponse.createServerResponseByFail("参数为空",null);
        }
        //查询商品数量
        Cart cart = cartMapper.selectCarByuserIdAndproductId(userId, productId);
        if (cart != null){
            //更新数量
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        return ServerResponse.createServerResponseBySuccess(getcartVOlimit(userId),null);
    }



//      移除购物车中某些商品
    @Override
    public ServerResponse delete_product(Integer userId, String productIds) {
        if (productIds == null || productIds.equals("")){
            return ServerResponse.createServerResponseByFail("参数为空",null);
        }
        List<Integer> productIdList = Lists.newArrayList();
        String[] productIdArr = productIds.split(",");
        if (productIdArr!=null && productIdArr.length>0){
            for (String productId : productIdArr){
                int i = Integer.parseInt(productId);
                productIdList.add(i);
            }
        }
        cartMapper.deleteproByuserIdAndproductId(userId,productIdList);
        return ServerResponse.createServerResponseBySuccess(getcartVOlimit(userId),null);
    }




//  购物车中选中某个商品
    @Override
    public ServerResponse select(Integer userId, Integer productId,Integer check) {
        cartMapper.selectOrUnselectByproductId(userId,productId,check);
        return ServerResponse.createServerResponseBySuccess(getcartVOlimit(userId),null);
    }


//  购物车中商品数量
    @Override
    public ServerResponse get_cart_product_count(Integer userId) {
        int cart_product_count = cartMapper.get_cart_product_count(userId);
        return ServerResponse.createServerResponseBySuccess(cart_product_count,null);
    }


    private CartVO getcartVOlimit(Integer userId){
        CartVO cartVO = new CartVO();
        //   根据用户id查询购物信息
        List<Cart> cartList = cartMapper.selectCarByuserId(userId);
        //    List<cart>==> List<CartProductVO>
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        //  定义购物车总价格
        BigDecimal carttotalprice = new BigDecimal("0");
        if (cartList != null && cartList.size()>0){
            for (Cart cart :cartList) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cart.getId());
                cartProductVO.setQuantity(cart.getQuantity());
                cartProductVO.setUserId(userId);
                cartProductVO.setProductChecked(cart.getChecked());
                //查询商品
                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product != null){
                    cartProductVO.setProductId(cart.getProductId());
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductStock(product.getStock());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    int stock = product.getStock();
                    int limitProductCount = 0;
                    if (stock> cart.getQuantity()){
                         limitProductCount=cart.getQuantity();
                         cartProductVO.setLimitQuantity("LIMIT_NUM_SUCCESS");
                    }else {//库存不足
                        limitProductCount = stock;
                        //更新购物车中库存数量
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(stock);
                        cart1.setProductId(cart.getProductId());
                        cart1.setChecked(cart.getChecked());
                        cart1.setUserId(userId);
                        cartMapper.updateByPrimaryKey(cart1);
                    }
                    cartProductVO.setQuantity(cart.getQuantity());
                    cartProductVO.setProductTotalPrice(BigDecimalUtils.mul(product.getPrice().doubleValue(),Double.valueOf(cartProductVO.getQuantity())));

                }
                //
                carttotalprice=BigDecimalUtils.add(carttotalprice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());

                cartProductVOList.add(cartProductVO);

            }
        }
        cartVO.setCartProductVO(cartProductVOList);
        cartVO.setAllprice(carttotalprice);
        int i = cartMapper.ischeckedAll(userId);
        if (i > 0 ){
            cartVO.setIsallchecked(false);
        }else{
            cartVO.setIsallchecked(true);
        }
        return cartVO;
    }
}
