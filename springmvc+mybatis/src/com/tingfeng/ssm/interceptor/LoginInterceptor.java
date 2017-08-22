package com.tingfeng.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //进行登录验证
        String url = httpServletRequest.getRequestURI();
//        在这里的url需要进行配置文件读取，在这里只是为了测试
            if (url.indexOf("login.action") > 0) {
            return true;
        }
        if (httpServletRequest.getSession(false) != null && httpServletRequest.getSession().getAttribute("username") != null) {
            //这里为了测试，其实验证还差一步，需要到数据库中去验证用户
            return true;
        }

        //走到这说明没有验证的访问
        //请求转发到登录页面
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(httpServletRequest,httpServletResponse);

        return false;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
