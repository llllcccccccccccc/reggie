package com.lc.reggie.service;

import com.lc.reggie.Dto.SetmealDto;
import com.lc.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author ASUS
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-11-20 11:28:42
*/
public interface SetmealService extends IService<Setmeal> {

    /**
     * 保存套餐和套餐对应的菜品关系数据
     * @param setmealDto
     */
    void saveSetmealWithSetmealDish(SetmealDto setmealDto);

    /**
     * 根据id查询套餐和套餐对应的菜品关系数据
     * @param id
     * @return
     */
    SetmealDto getSetmealDtoById(Long id);

    /**
     * 修改套餐和套餐对应的菜品关系数据
     * @param setmealDto
     */
    void updateSetmealWithSetmealDish(SetmealDto setmealDto);

    /**
     * 删除套餐和套餐对应的菜品关系数据
     * @param ids
     */
    String deleteSetmealWithSetmealDish(List<Long> ids);


    /**
     * 根据id修改套餐状态 修改为 0
     * @param ids
     */
    void updateStatus0(List<Long> ids);

    /**
     * 根据id修改套餐状态 修改为 1
     * @param ids
     */
    void updateStatus1(List<Long> ids);
}
