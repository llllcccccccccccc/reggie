package com.lc.reggie.service;

import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ASUS
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-11-20 10:07:02
*/
public interface CategoryService extends IService<Category> {

    /**
     * 删除菜品
     * @return
     */
    R<String> remove(Long id);
}
