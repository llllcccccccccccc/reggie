package com.lc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.lc.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 新增菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增菜品分类成功");
    }

    /**
     * 分页查询功能
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        log.info("page={}, pageSize={}", page, pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        //根据姓名字段的模糊查询，和给修改时间字段的倒叙
        queryWrapper.orderByAsc(Category::getSort);
        //执行查询
        categoryService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据Id修改菜品分类
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info(category.toString());

        categoryService.updateById(category);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据Id删除菜品分类
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> remove(@RequestParam("ids") Long ids) {
        log.info("删除的餐品id是：{}",ids);
        return categoryService.remove(ids);
    }


    //localhost:8080/category/list?type=1
    @GetMapping("/list")
    public R<List> getCategoryByType(@RequestParam(value = "type",required = false) Integer type){
        List<Category> list;
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        if (type!=null) {
            wrapper.eq(Category::getType, type);
        }
        list = categoryService.list(wrapper);
        return R.success(list);
    }
}
