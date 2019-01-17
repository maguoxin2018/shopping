package com.neuedu.service.Imp;

import com.google.common.collect.Sets;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class ICategoryserviceImp implements ICategoryservice {
    @Autowired
    CategoryMapper categoryMapper;



    //    1.获取品类子节点(平级)
    @Override
    public ServerResponse get_category(Integer categoryId) {
//        setp:1    非空校验
        if (categoryId == null){
            return ServerResponse.createServerResponseByFail("参数不能为空");
        }
//         setp:2   根据categoryId查询类别
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerResponse.createServerResponseByFail("查询的类别不存在");
        }
//         SETP:3    查询子类别
        List<Category> categories = categoryMapper.selectChildCategoryByCategoryId(categoryId);
//         SETP:4    返回结果
        return ServerResponse.createServerResponseBySuccess(categories,null);
    }



    //    2.增加节点(平级)

    @Override
    public ServerResponse add_category(Integer parentId, String categoryName) {
        //step1：参数校验
        if (categoryName == null ||categoryName.equals("")){
            return ServerResponse.createServerResponseByFail("类别名称不能为空");
        }
        //step2：添加节点
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(1);
        int insert = categoryMapper.insert(category);
        //step3：返回结果
        if (insert >0){
            return ServerResponse.createServerResponseBySuccess();
        }
        return ServerResponse.createServerResponseByFail("添加失败");
    }



    //    3.修改节点
    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {
        //step1：参数非空校验
        if (categoryId == null ||categoryId.equals("")){
            return ServerResponse.createServerResponseByFail("类别id不能为空");
        }
        if (categoryName == null ||categoryName.equals("")){
            return ServerResponse.createServerResponseByFail("类别名称不能为空");
        }
        //step2：根据categoryId查询
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category == null){
            return ServerResponse.createServerResponseByFail("要修改的类别不存在");
        }
        //step3：修改
        category.setName(categoryName);
        int i = categoryMapper.updateByPrimaryKey(category);
        //step4：返回结果
        if (i>0){
            //修改成功
            return ServerResponse.createServerResponseBySuccess("修改成功");
        }
        return ServerResponse.createServerResponseByFail("添加失败");
    }



    //   获取当前分类id及递归子节点categoryId
    @Override
    public ServerResponse get_deep_category(Integer categoryId) {
        //step1：参数非空验证
        if (categoryId == null){
            return ServerResponse.createServerResponseByFail("类别id不能为空");
        }
        //step2：查询
        Set<Category> categorySet = Sets.newHashSet();
        categorySet = findAllChildCategory(categorySet, categoryId);
        //转换一下集合的类型
        Set<Integer> integersSet = Sets.newHashSet();
        //遍历set集合
        Iterator<Category> iterator = categorySet.iterator();
        while (iterator.hasNext()){
            Category category = iterator.next();
            integersSet.add(category.getId());
        }
        return ServerResponse.createServerResponseBySuccess(integersSet,null);
    }

    private Set<Category> findAllChildCategory(Set<Category> categorySet,Integer categoryId){
        //查找本节点
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);//id一样为同一个类（重写category的equals方法）
        }
        //查找categoryId下的子节点（平级）
        List<Category> childCategory = categoryMapper.selectChildCategoryByCategoryId(categoryId);
//        递归结束的条件，当集合为空或者集合的大小为0时，结束递归
        if (childCategory != null&&childCategory.size()>0){
            for (Category category1:childCategory){
//                自己调用自己，递归
                findAllChildCategory(categorySet,category1.getId());
            }
        }
        return categorySet;
    }
}
