package com.lc.reggie.mapper;

import com.lc.reggie.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-11-28 15:49:06
* @Entity com.lc.reggie.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




