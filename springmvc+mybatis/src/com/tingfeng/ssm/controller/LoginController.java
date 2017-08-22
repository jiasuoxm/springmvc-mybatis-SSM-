package com.tingfeng.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class LoginController {

    @RequestMapping(value = "/login.action")
    public String login(HttpSession session,String username, String password) throws Exception {
        //调用service方法执行验证
        /*
        ...
         */
        session.setAttribute("username", username);
        //重定向到商品查询列表
        return "redirect:/items/queryItems.action";
    }

    @RequestMapping(value = "/logout.action")
    public String logout(HttpSession session) throws Exception {
        //调用service方法执行验证
        session.invalidate();
        //重定向到商品查询列表
        return "forward:/items/queryItems.action";
    }

}
