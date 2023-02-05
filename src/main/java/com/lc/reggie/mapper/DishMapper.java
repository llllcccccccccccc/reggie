package com.lc.reggie.mapper;

import com.lc.reggie.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-11-20 11:28:47
* @Entity com.lc.reggie.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




