package com.lc.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lc.reggie.common.R;
import com.lc.reggie.entity.User;
import com.lc.reggie.service.UserService;
import com.lc.reggie.utils.SMSUtils;
import com.lc.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode4String(4);
            log.info("code:{}",code);
            //调用阿里云的断行服务API完成发送短信
//            SMSUtils.sendMessage("天天外卖","",phone,code);
            //需要将生成的验证码保存到session
            session.setAttribute(phone,code);
        return R.success("短信发送成功");
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中获取后台生成的验证码
        Object codeInSession = session.getAttribute(phone);
        //后台生成的验证码与前台发送的验证码进行比对
        if (codeInSession!=null &&codeInSession.equals(code)){
            //判断用户是否存在，不存在则帮其创建一个账户
            User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, phone));
            if (user==null){
                //自动注册
               user = new User();
               user.setPhone(phone);
               user.setStatus(1);
               userService.save(user);
            }
            session.setAttribute("userId",user.getId());
            return R.success(user);
        }

        return R.error("登录失败");
    }

}
