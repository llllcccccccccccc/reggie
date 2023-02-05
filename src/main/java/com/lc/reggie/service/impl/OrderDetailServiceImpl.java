package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.OrderDetail;
import com.lc.reggie.service.OrderDetailService;
import com.lc.reggie.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-11-29 13:51:43
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




