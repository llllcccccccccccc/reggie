package com.lc.reggie.service;

import com.lc.reggie.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author ASUS
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-11-29 13:51:48
*/
public interface OrdersService extends IService<Orders> {


    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);



}
