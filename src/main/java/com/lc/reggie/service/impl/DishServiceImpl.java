package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.Dto.DishDto;
import com.lc.reggie.entity.Dish;
import com.lc.reggie.entity.DishFlavor;
import com.lc.reggie.service.DishFlavorService;
import com.lc.reggie.service.DishService;
import com.lc.reggie.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ASUS
 * @description 针对表【dish(菜品管理)】的数据库操作Service实现
 * @createDate 2022-11-20 11:28:47
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);
        //新增菜品对应的口味
        List<DishFlavor> flavors = dishDto.getFlavors();
//        批量保存
//        方式一：以流的形式，对属性赋值，再转为list,最后saveBatch(),进行批处理
//        flavors=flavors.stream().map((item) ->{
//            item.setDishId(dishDto.getId());
//            return item;
//        }).collect(Collectors.toList());
//        dishFlavorService.saveBatch(flavors);

//        方式二：
        for (int i = 0; i < flavors.size(); i++) {
            flavors.get(i).setDishId(dishDto.getId());
            dishFlavorService.save(flavors.get(i));
        }


    }

    @Transactional
    @Override
    public void removeWithFlavor(List<Long> ids) {
        //删除菜品
        this.removeByIds(ids);
        //删除菜品对应的口味信息
        for (int i = 0; i < ids.size(); i++) {
            LambdaQueryWrapper<DishFlavor> LambdaQueryWrapper = new LambdaQueryWrapper<>();
            LambdaQueryWrapper.eq(DishFlavor::getDishId, ids.get(i));
            dishFlavorService.remove(LambdaQueryWrapper);
        }
    }

    @Override
    @Transactional
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        //得到菜品对象
        Dish dish = this.getById(id);
        //拷贝信息
        BeanUtils.copyProperties(dish, dishDto);
        //得到口味
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        //得到 菜品口味的集合
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //修改菜品
        this.updateById(dishDto);
        //修改对应的口味,应该先将原来的口味删除，在重新添加新的口味
        //删除
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);

        //添加
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (int i = 0; i < flavors.size(); i++) {
            flavors.get(i).setDishId(dishDto.getId());
            dishFlavorService.save(flavors.get(i));
        }
    }
}




