package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.lc.reggie.entity.Dish;
import com.lc.reggie.entity.Setmeal;
import com.lc.reggie.service.CategoryService;
import com.lc.reggie.mapper.CategoryMapper;
import com.lc.reggie.service.DishService;
import com.lc.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
* @author ASUS
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-11-20 10:07:02
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据Id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public R<String> remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        //查询当前分类是否包含菜品
        long dishSize = dishService.count(dishLambdaQueryWrapper);
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        //查询当前分类是否包含套餐
        long setmealSize = setmealService.count(setmealLambdaQueryWrapper);

        if (setmealSize>0||dishSize>0){
            throw new ClassCastException("当前分类下关联了菜品或套餐，不能删除");
//            return R.error("删除异常");
        }
        removeById(id);
        return R.success("删除套餐成功");
    }
}




