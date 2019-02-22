package com.neuedu.controller.portal;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//   前台商品管理
@RestController
@RequestMapping(value = "/product")
public class ProductController {


    @Autowired
    IProductservice iProductservice;

//      商品详情
    @RequestMapping(value = "/detail.do/{productId}")
    public ServerResponse detail_portal(@PathVariable Integer productId){
        return iProductservice.detail_portal(productId);
    }
//      搜索商品以及动态排序
    @RequestMapping(value = "/list.do")
     public ServerResponse list_portal(@RequestParam(required = false)Integer categoryId,
                                @RequestParam(required = false)String keyword,
                                @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum,
                                @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                @RequestParam(required = false,defaultValue = "")String orderBy){
        return iProductservice.list_portal(categoryId,keyword,pageNum,pageSize,orderBy);
    }

}
