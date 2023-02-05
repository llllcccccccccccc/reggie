package com.lc.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.reggie.Dto.DishDto;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.lc.reggie.entity.Dish;
import com.lc.reggie.entity.DishFlavor;
import com.lc.reggie.service.CategoryService;
import com.lc.reggie.service.DishFlavorService;
import com.lc.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 菜品管理
 */
@RequestMapping("/dish")
@RestController
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品
     * 同时对Dish表和dish_flavor表进行操作
     *
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info("{}", dishDto.toString());
        //新增菜品
        dishService.saveWithFlavor(dishDto);

        return R.success("新增成功！");
    }

    /**
     * 分页查询功能
     *
     * @param page
     * @param pageSize
     * @param name     required = false 该参数是否为必传项
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(required = false, value = "name") String name) {
        //构造分页构造器
        Page<Dish> dishPage = new Page(page, pageSize);
        Page<DishDto> dishDtoPage = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);
        //执行查询
        dishService.page(dishPage, queryWrapper);

        //对象拷贝
        //将 dishPage 的属性内容拷贝给 dishDtoPage，忽略拷贝records属性
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
/**内容拷贝方式一：
 //        List<Dish> records = dishPage.getRecords();
 ////        流的形式遍历
 //        List<DishDto> list = records.stream().map((item) -> {
 //            DishDto dishDto = new DishDto();
 //
 //            //将Dish的属性拷贝给DishDto
 //            BeanUtils.copyProperties(item, dishDto);
 //
 //            Long categoryId = item.getCategoryId();//分类Id
 //            //根据Id查询分类对象
 //            Category category = categoryService.getById(categoryId);
 //              if (category!=null){
 //                //赋值分类名属性
 //                dishDto.setCategoryName(category.getName());
 //            }
 //            return dishDto;
 //        }).collect(Collectors.toList());
 //        dishDtoPage.setRecords(list);
 */

        //内容拷贝方式二
        List<Dish> dishList = dishPage.getRecords();
        List<DishDto> dtoList = new ArrayList<>();
        for (int i = 0; i < dishList.size(); i++) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishList.get(i), dishDto);
            //得到分类id
            Long categoryId = dishList.get(i).getCategoryId();
            //根据id得到分类
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //赋值分类名属性
                dishDto.setCategoryName(category.getName());
            }
            dtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dtoList);


        return R.success(dishDtoPage);
    }

    /**
     * 根据Id查找菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable("id") Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     *
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);

        return R.success("修改成功！");
    }

    /**
     * 删除菜品
     * 同时对Dish表和dish_flavor表进行操作
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @Transactional
    public R<String> removeWhitFlavor(@RequestParam("ids") List<Long> ids) {

        log.info("要删除的菜品id{}", ids);
        dishService.removeWithFlavor(ids);
        return R.success("删除成功");
    }


    /**
     * 根据菜品分类id获取菜品集合
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> getDishByCategoryId(Dish dish) {

        List<Dish> dishList = dishService.list(
                new LambdaQueryWrapper<Dish>()
                        .eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                        .eq(Dish::getStatus, 1)
        );

        ArrayList<DishDto> dishDtos = new ArrayList<>();
        for (int i = 0; i < dishList.size(); i++) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dishList.get(i), dishDto);
            List<DishFlavor> flavors = dishFlavorService.list(
                    new LambdaQueryWrapper<DishFlavor>()
                            .eq(DishFlavor::getDishId, dishList.get(i).getId()));

            dishDto.setFlavors(flavors);

            dishDtos.add(dishDto);
        }

        return R.success(dishDtos);
    }
//    /**
//     * 根据菜品分类id获取菜品集合
//     * @param dish
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Dish>> getDishByCategoryId(Dish dish){
//        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(dish.getCategoryId() !=null,Dish::getCategoryId,dish.getCategoryId()).eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(wrapper);
//        return R.success(list);
//    }
}


