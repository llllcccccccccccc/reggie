package com.lc.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Category;
import com.lc.reggie.entity.Orders;
import com.lc.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        ordersService.submit(orders);
        return R.success("下单成功");
    }


    /**
     * 分页查询功能
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        log.info("page={}, pageSize={}", page, pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper();
        //根据姓名字段的模糊查询，和给修改时间字段的倒叙
        queryWrapper.orderByAsc(Orders::getOrderTime);
        //执行查询
        ordersService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

}
