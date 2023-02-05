package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.ShoppingCart;
import com.lc.reggie.service.ShoppingCartService;
import com.lc.reggie.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-11-29 09:38:57
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}




