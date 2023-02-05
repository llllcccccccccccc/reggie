package com.lc.reggie.service;

import com.lc.reggie.Dto.DishDto;
import com.lc.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-11-20 11:28:47
*/
public interface DishService extends IService<Dish> {

    /**
     * 添加菜品和添加菜品的口味信息
     * @param dishDto
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 删除菜品和删除菜品对应的口味信息
     * @param ids
     */
    void removeWithFlavor(List<Long> ids);

    /**
     * 根据Id查询菜品和其对应的口味信息
     * @param id
     * @return
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 修改菜品和修改菜品对应的口味信息
     * @param dishDto
     */
    void updateWithFlavor(DishDto dishDto);
}
