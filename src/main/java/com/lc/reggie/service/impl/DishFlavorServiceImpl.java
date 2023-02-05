package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.DishFlavor;
import com.lc.reggie.service.DishFlavorService;
import com.lc.reggie.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-11-20 20:43:28
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}




