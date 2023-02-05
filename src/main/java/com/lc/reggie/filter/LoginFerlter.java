package com.lc.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.lc.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(value = "/*")
public class LoginFerlter implements Filter {

    //路径匹配器，支持通配符写法
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取本次请求的URI
        String requestURI = req.getRequestURI();
        log.info("拦截到请求： {}", requestURI);


        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //路径不需要处理，直接放行
        boolean check = check(urls, req.getRequestURI());
        if (check){
            log.info("本次请求{}不需要处理",req.getRequestURI());
            chain.doFilter(request, response);
            return;
        }
        //判断是登录过，登录过的用户SessionId会保存在会话作用域
       Object employeeId =  req.getSession().getAttribute("employeeId");
        if (employeeId !=null){
            log.info("用户登录成功！id为{}",employeeId);
            chain.doFilter(request, response);
            return;
        }
        //判断是登录过，登录过的用户SessionId会保存在会话作用域
       Object userId =  req.getSession().getAttribute("userId");
        if (userId !=null){
            log.info("用户登录成功！id为{}",userId);
            chain.doFilter(request, response);
            return;
        }


        log.info("用户未登录");
//        通过输出流的方式响应数据,返回该信息，前端页面的拦截器，接收到 NOTLOGIN  会自动跳到登录页
        resp.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
        //匹配url
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
