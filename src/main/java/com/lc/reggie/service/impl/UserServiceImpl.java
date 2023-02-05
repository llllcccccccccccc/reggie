package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.User;
import com.lc.reggie.service.UserService;
import com.lc.reggie.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-11-28 15:49:06
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




