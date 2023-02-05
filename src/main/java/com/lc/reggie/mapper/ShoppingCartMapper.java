package com.lc.reggie.mapper;

import com.lc.reggie.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-11-29 09:38:57
* @Entity com.lc.reggie.entity.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




