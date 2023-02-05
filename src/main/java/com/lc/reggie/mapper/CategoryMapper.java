package com.lc.reggie.mapper;

import com.lc.reggie.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-11-20 10:07:02
* @Entity com.lc.reggie.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




