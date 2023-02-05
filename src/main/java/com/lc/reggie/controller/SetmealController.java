package com.lc.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.reggie.Dto.SetmealDto;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.lc.reggie.entity.Setmeal;
import com.lc.reggie.service.CategoryService;
import com.lc.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐管理
 */
@RequestMapping("/setmeal")
@RestController
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

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
        Page<Setmeal> setmealPage = new Page(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(setmealPage, queryWrapper);

        //对象拷贝
        //将 setmealPage 的属性内容拷贝给 setmealDtoPage，忽略拷贝records属性
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

        //获取数据列表
        List<Setmeal> setmealList = setmealPage.getRecords();
        List<SetmealDto> dtoList = new ArrayList<>();
        for (int i = 0; i < setmealList.size(); i++) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmealList.get(i), setmealDto);
            //得到分类id
            Long categoryId = setmealList.get(i).getCategoryId();
            //根据id得到分类
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //赋值分类名属性
                setmealDto.setCategoryName(category.getName());
            }
            dtoList.add(setmealDto);
        }
        setmealDtoPage.setRecords(dtoList);

        return R.success(setmealDtoPage);
    }

    /**
     * 添加套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("套餐{}", setmealDto);
        setmealService.saveSetmealWithSetmealDish(setmealDto);

        return R.success("套餐添加成功！");
    }

    /**
     * 根据id查询套餐和对应的套餐关系数据
     *
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealDtoById(@PathVariable("id") Long id) {

        SetmealDto setmealDto = setmealService.getSetmealDtoById(id);

        return R.success(setmealDto);
    }

    /**
     * 修改套餐和套餐对应的信息
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto) {

        setmealService.updateSetmealWithSetmealDish(setmealDto);

        return R.success("套餐修改成功！");
    }


    /**
     * 删除套餐和对应的关系数据
     *
     * @return
     */
    @DeleteMapping
    public R<String> remove(@RequestParam("ids") List<Long> ids) {

        String msg = setmealService.deleteSetmealWithSetmealDish(ids);
        return R.success(msg);
    }

    /**
     * 根据id修改套餐状态 修改为  0
     *
     * @return
     */
    @PostMapping("/status/0")
    public R<String> updateStatus0(@RequestParam("ids") List<Long> ids) {
        setmealService.updateStatus0(ids);
        return R.success("套餐状态修改成功");
    }

    /**
     * 根据id修改套餐状态 修改为  1
     *
     * @return
     */
    @PostMapping("/status/1")
    public R<String> updateStatus1(@RequestParam("ids") List<Long> ids) {
        setmealService.updateStatus1(ids);
        return R.success("套餐状态修改成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, 1)
                .orderByDesc(Setmeal::getPrice);
        List<Setmeal> list = setmealService.list(wrapper);
        return R.success(list);
    }
}
