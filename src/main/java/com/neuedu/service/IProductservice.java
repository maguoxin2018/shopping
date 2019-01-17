package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public interface IProductservice {
//    新增or更新商品
    ServerResponse addOrUpdate (Product product);
//    商品上下架管理
    ServerResponse set_sale_status(Integer productId, Integer status);
//    后台——商品详情
    ServerResponse detail(Integer productId);
//    查看商品列表
    ServerResponse list(Integer pageNum, Integer pageSize);
//    搜索商品
    ServerResponse search(Integer productId,String productName,Integer pageNum, Integer pageSize);
//    上传图片
    ServerResponse upload(MultipartFile file,String path);
//    前台——商品详情
    ServerResponse detail_portal(Integer productId);
//    前台——搜索商品
    ServerResponse list_portal(Integer categoryId, String keyword, Integer pageNum, Integer pageSize,String orderBy);
}
