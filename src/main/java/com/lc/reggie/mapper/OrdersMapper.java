package com.lc.reggie.mapper;

import com.lc.reggie.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-11-29 13:51:48
* @Entity com.lc.reggie.entity.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




