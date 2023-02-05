package com.lc.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.Employee;
import com.lc.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录(POST)
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

//        1.将页面提交的密码进行加密处理MD5()
        String password = employee.getPassword();
        //将获取的密码，进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());

//        2.根据用户名查，是否存在用户，失败返回结果
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if (emp == null) {
            return R.error("用户名错误！");
        }
//        3.密码比对，不一致则返回结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }
//        4.查看员工状态，如果该员工被禁用，则返回失败结果
        if (emp.getStatus() == 0) {
            return R.error("账号被禁止访问");
        }
//        5.登录成功，将员工sessionID存入Session并返回登录 成功 结果
        Long id = emp.getId();
        request.getSession().setAttribute("employeeId", id);

        return R.success(emp);
    }

    /**
     * 后台退出功能（POST）
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {

//                * 1.清理Session中的用户ID
        request.getSession().removeAttribute("employee");

//                * 2.返回结果
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        //设置初始密码123456，MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeService.save(employee);

        return R.success("新增员工成功！");
    }

    /**
     * 分页查询功能
     *
     * @param page
     * @param pageSize
     * @param name     required = false 该参数是否为必传项
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize, @RequestParam(required = false, value = "name") String name) {
        log.info("page={}, pageSize={}, name={}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //根据姓名字段的模糊查询，和给修改时间字段的倒叙
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name)
                .orderByDesc(Employee::getUpdateTime);
        //执行查询
        employeeService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据Id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        log.info(employee.toString());


        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * 根据Id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查到对应员工信息");
    }
}
