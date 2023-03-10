package com.lc.reggie.mapper;

import com.lc.reggie.entity.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-11-28 19:46:43
* @Entity com.lc.reggie.entity.AddressBook
*/
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}




