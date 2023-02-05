package com.lc.reggie.mapper;

import com.lc.reggie.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-11-29 13:51:43
* @Entity com.lc.reggie.entity.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




