package com.lc.reggie.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());
//        如果错误信息包含 Duplicate entry ，那么那就是字段重复错误
        if (ex.getMessage().contains("Duplicate entry")) {
// 错误信息为：Duplicate entry 'zhangsan' for key 'employee.idx_username'
            String[] split = ex.getMessage().split(" ");
            //得到第二个索引的元素： 'zhangsan'，并拼接字符串
            String msg = split[2] + "已存在！";
            return R.error(msg);
        }
        return R.error("未知错误！");
    }
    /**
     * 异常处理方法
     *
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> CustomException(CustomException ex) {
        log.error(ex.getMessage());
//        如果错误信息包含 Duplicate entry ，那么那就是字段重复错误
        return R.error(ex.getMessage());
    }

}
