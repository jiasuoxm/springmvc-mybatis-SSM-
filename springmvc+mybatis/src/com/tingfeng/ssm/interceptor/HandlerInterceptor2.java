package com.tingfeng.ssm.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Handler interceptor 1.
 * 测试拦截器
 */
public class HandlerInterceptor2 implements HandlerInterceptor {

    //执行时机：进入handler（controller）之前执行
    //用于身份认证(登录认证)、身份授权(权限校验)
    //比如身份认证，如果认证不通过，表示当前用户没有登录，需要此方法拦截，不再向下执行
    //return false表示拦截住，不想下执行
    //return true表示放行
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("2-------preHandle");
        return true;
    }


    //进入handler方法之后返回modelAndView之前执行
    //应用场景从ModelAndView出发：将公用的模型数据（比如菜单的导航）在这里传到视图，也可以在这里统一的指定视图
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("2-------postHandle");
    }


    //执行handler方法之后执行
    //统一的异常处理，统一的日志处理
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("2-------afterCompletion");
    }
}
