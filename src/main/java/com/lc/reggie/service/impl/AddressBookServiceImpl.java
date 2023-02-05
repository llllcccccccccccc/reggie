package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.AddressBook;
import com.lc.reggie.service.AddressBookService;
import com.lc.reggie.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-11-28 19:46:43
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




