package com.lc.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.reggie.entity.Employee;
import com.lc.reggie.service.EmployeeService;
import com.lc.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-11-18 15:26:29
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}




