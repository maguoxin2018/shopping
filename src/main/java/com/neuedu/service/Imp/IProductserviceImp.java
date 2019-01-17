package com.neuedu.service.Imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.neuedu.VO.ProductDetailVO;
import com.neuedu.VO.ProductListVO;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryservice;
import com.neuedu.service.IProductservice;
import com.neuedu.utils.DateUtils;
import com.neuedu.utils.FTPUtil;
import com.neuedu.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class IProductserviceImp implements IProductservice {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    ICategoryservice iCategoryservice;

//    新增or更新商品
    @Override
    public ServerResponse addOrUpdate(Product product) {
    //        setp：1    参数校验
        if (product ==null){
            return ServerResponse.createServerResponseByFail("商品为空",null);
        }
    //        setp：2    设置商品主图
        String subImages = product.getSubImages();
        if (subImages==null &&  !subImages.equals("")){
    //            将拿到的字符串数组按照“，”分隔开
            String[] split = subImages.split(",");
            if (split.length>0){
    //                设置商品的主图
                product.setMainImage(split[0]);
            }
        }
    //        setp：3    判断是添加还是更新
        if (product.getId() == null){
            //添加
            int insert = productMapper.insert(product);
            if (insert > 0){
                return ServerResponse.createServerResponseBySuccess();
            }else {
                return ServerResponse.createServerResponseByFail("添加失败",null);
            }
        }else{
            //更新
            int insert = productMapper.updateByPrimaryKey(product);
            if (insert > 0){
                return ServerResponse.createServerResponseBySuccess();
            }else {
                return ServerResponse.createServerResponseByFail("更新失败",null);
            }
        }
    }



//    管理商品上下架
    @Override
    public ServerResponse set_sale_status(Integer productId, Integer status) {
    //        setp：1    非空校验
        if (productId == null){
            return ServerResponse.createServerResponseByFail("商品id不能为空");
        }
        if (status == null){
            return ServerResponse.createServerResponseByFail("商品状态不能为空");
        }
    //        setp：2    修改商品状态
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int i = productMapper.updateStatusByProduct(product);
        if (i > 0){
            return ServerResponse.createServerResponseBySuccess();
        }else {
            return ServerResponse.createServerResponseByFail("更新失败",null);
        }
    }



//    后台——商品详情
    @Override
    public ServerResponse detail(Integer productId) {
    //        setp：1    非空校验
        if (productId == null){
            return ServerResponse.createServerResponseByFail("商品id不能为空");
        }
    //        setp：2    查询商品信息
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createServerResponseByFail("失败",null);
        }
    //        setp:3   product==> ProductDetailVO
        ProductDetailVO productDetailVO = TransformationProduct(product);
    //            返回结果
        return ServerResponse.createServerResponseBySuccess(productDetailVO,null);
    }



//   前台——商品详情
    @Override
    public ServerResponse detail_portal(Integer productId) {
        //step1:参数的非空校验
        if (productId == null){
            return ServerResponse.createServerResponseByFail("商品id不能为空");
        }
        //step2：查询商品（product）
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createServerResponseByFail("商品不存在");
        }
        //step3：校验商品状态
        if (product.getStatus()!= Const.ProductStatusEnum.PRODUCT_ONLINE.getCode()){
            return ServerResponse.createServerResponseByFail("商品已下架或删除");
        }
        //step4：获取productDetailVO
        ProductDetailVO productDetailVO = TransformationProduct(product);
        //step5：返回结果
        return ServerResponse.createServerResponseBySuccess(productDetailVO,null);
    }

    private ProductDetailVO TransformationProduct(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setCreateTime(DateUtils.dateToString(product.getCreateTime()));
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setImageHost(PropertiesUtils.readBykey("imageHost"));
        productDetailVO.setName(product.getName());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setId(product.getId());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setUpdateTime(DateUtils.dateToString(product.getUpdateTime()));
        //查询父类ID
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category != null){
            productDetailVO.setParentCategoryId(category.getParentId());
        }else {
            //默认根节点
            productDetailVO.setParentCategoryId(0);
        }
        return productDetailVO;
    }


//    查看商品列表
    @Override
    public ServerResponse list(Integer pageNum, Integer pageSize) {
        //必需放在查询语句前（实现分页）
        PageHelper.startPage(pageNum,pageSize);
        //查询商品数据
        List<Product> productslist = productMapper.selectAll();
        ArrayList<ProductListVO> ProductListVO = Lists.newArrayList();
        if(productslist != null && productslist.size()>0){
            for (Product p:productslist) {
                ProductListVO productListVO = assembleProductListVO(p);
                ProductListVO.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(ProductListVO);
        return ServerResponse.createServerResponseBySuccess(pageInfo,null);
    }
    private ProductListVO  assembleProductListVO(Product product){
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setMainImage(product.getMainImage());
        productListVO.setName(product.getName());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return productListVO;
    }


//    搜索商品
    @Override
    public ServerResponse search(Integer productId, String productName, Integer pageNum, Integer pageSize) {
        //必需放在查询语句前（实现分页）
        PageHelper.startPage(pageNum,pageSize);
        if (productName != null && !productName.equals("")){
            productName = "%"+productName+"%";
        }else {
            productName = null;
        }
        List<Product> products = productMapper.selectproductByProductIdAndProductName(productId, productName);
        List<ProductListVO> ProductListVO = Lists.newArrayList();
        if(products != null && products.size()>0){
            for (Product p:products) {
                ProductListVO productListVO = assembleProductListVO(p);
                ProductListVO.add(productListVO);
            }
        }
        PageInfo pageInfo = new PageInfo(ProductListVO);
        return ServerResponse.createServerResponseBySuccess(pageInfo,null);
    }





//      图片上传
    @Override
    public ServerResponse upload(MultipartFile file,String path) {
        if (file == null){
            return ServerResponse.createServerResponseByFail("上传图片为空",null);
        }
        //获取上传文件的文件名
        String originalFilename = file.getOriginalFilename();
        //截取上传文件的后缀
        String substr = originalFilename.substring(originalFilename.lastIndexOf("."));
        //是上传文件多的文件名唯一，生成一个新的名字
        String newfile=UUID.randomUUID().toString()+substr;
        //判断路径下文件是否存在，不存在创建一个
        File file1 = new File(path);
        if (!file1.exists()){
            file1.setWritable(true);
            file1.mkdirs();
        }
//        将新生成的文件寸放到该路径下
        File file2 = new File(path,newfile);
        try {
            file.transferTo(file2);
            //上传到图片服务器
            FTPUtil.uploadFile(Lists.newArrayList(file2));
            Map<String,String> map = Maps.newHashMap();
            map.put("uri",newfile);
            map.put("url",PropertiesUtils.readBykey("imgHost")+"/"+newfile);
            file2.delete();
            return ServerResponse.createServerResponseBySuccess(map,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }






//      前台_搜索商品以及动态排序
    @Override
    public ServerResponse list_portal(Integer categoryId,String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        //setp:1   参数校验
        if (categoryId == null&& keyword == null||keyword.equals("")){
            return ServerResponse.createServerResponseByFail("参数错误",null);
        }
        //setp:2
        Set<Integer> date = Sets.newHashSet();
        if (categoryId != null){
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null&& keyword==null ||keyword.equals("")){//说明没有数据
                PageHelper.startPage(pageNum,pageSize);
                ArrayList<ProductListVO> LIST= Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(LIST);
                return ServerResponse.createServerResponseBySuccess(pageInfo,null);
            }
            ServerResponse deep_category = iCategoryservice.get_deep_category(categoryId);
            if (deep_category.isSuccess()){
                date = (Set<Integer>)deep_category.getDate();
            }
        }
        //setp:3   keyword
        if (keyword != null && !keyword.equals("")){
            keyword = "%"+ keyword +"%";
        }else{
            keyword = null;
        }
        if (orderBy.equals("")){
            PageHelper.startPage(pageNum,pageSize);
        }else {
            String[] orderByArry = orderBy.split("_");
            if (orderByArry.length>1){
                PageHelper.startPage(pageNum,pageSize,orderByArry[0]+" "+orderByArry[1]);
            }else {
                PageHelper.startPage(pageNum,pageSize);
            }
        }
        //  setp:4  将product转化成ProductListVO
        List<Product> selectproductportal = productMapper.selectproductportal(date, keyword);
        List<ProductListVO> LIST= Lists.newArrayList();
        if (selectproductportal != null && selectproductportal.size()>0){
            for (Product product:selectproductportal){
                ProductListVO productListVO = assembleProductListVO(product);
                LIST.add(productListVO);
            }
        }
        //setp:5   分页
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(LIST);
        //  返回结果
        return ServerResponse.createServerResponseBySuccess(pageInfo,null);
    }
}
