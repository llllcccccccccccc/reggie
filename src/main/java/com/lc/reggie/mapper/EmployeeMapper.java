package com.lc.reggie.mapper;

import com.lc.reggie.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-11-18 15:26:29
* @Entity com.lc.reggie.entity.Employee
*/

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




