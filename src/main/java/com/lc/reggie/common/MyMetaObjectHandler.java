package com.lc.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private HttpServletRequest request;

    /**
     * 插入的时候自动填充
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
      this.insertAndUpdate(metaObject);
    }

    /**
     * 修改时自动填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
      this.insertAndUpdate(metaObject);
    }

    /**
     * INSERT_UPDATE和INSERT，需要填充的公共字段
     * @param metaObject
     */
    public void  insertAndUpdate(MetaObject metaObject){
        Object employee = request.getSession().getAttribute("employeeId");
        Object user = request.getSession().getAttribute("userId");
        String[] names = metaObject.getSetterNames();
        if (employee != null) {
            for (String name : names) {
                switch (name){
                    case "createTime":{
                        metaObject.setValue(name, LocalDateTime.now());
                        break;
                    }
                    case "updateTime":{
                        metaObject.setValue(name, LocalDateTime.now());
                        break;
                    }
                    case "createUser":{
                        metaObject.setValue(name, employee);
                        break;
                    }
                    case "updateUser":{
                        metaObject.setValue(name, employee);
                        break;
                    }
                }
            }

        }
        if (user != null) {
            for (String name : names) {
                switch (name){
                    case "createTime":{
                        metaObject.setValue(name, LocalDateTime.now());
                        break;
                    }
                    case "updateTime":{
                        metaObject.setValue(name, LocalDateTime.now());
                        break;
                    }
                    case "createUser":{
                        metaObject.setValue(name, user);
                        break;
                    }
                    case "updateUser":{
                        metaObject.setValue(name, user);
                        break;
                    }}
            }
        }
    }
}
