package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICategoryservice {
    //    1.获取品类子节点(平级)
    ServerResponse get_category(Integer categoryId);
    //    2.增加节点(平级)
    ServerResponse add_category(Integer parentId, String categoryName);
    //    3.修改节点
    ServerResponse set_category_name(Integer categoryId,String categoryName);
    //获取当前分类id及递归子节点categoryId
    ServerResponse get_deep_category(Integer categoryId);
}
